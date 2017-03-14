package com.daykm.tiger.features.authentication;

import com.daykm.tiger.features.base.BuildConstants;
import com.daykm.tiger.features.data.realm.domain.TwitterServiceCredentials;
import com.daykm.tiger.features.services.AccessTokenService;
import com.daykm.tiger.features.services.AuthenticationService;
import com.daykm.tiger.features.services.TwitterApp;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.IOException;
import java.util.Arrays;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class AuthPresenter implements AuthContract.Presenter {

  AuthenticationService authService;

  AccessTokenService tokenService;

  AuthContract.View view;

  BuildConstants constants;

  @Inject AuthPresenter(AuthenticationService authService, AccessTokenService tokenService,
      BuildConstants constants) {
    this.authService = authService;
    this.tokenService = tokenService;
    this.constants = constants;
  }

  @Override public void attach(final AuthContract.View view) {
    this.view = view;

    authService.getRequestToken().enqueue(new Callback<ResponseBody>() {
      @Override @DebugLog
      public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
        if (response.code() == 200) {
          try {
            String url = "https://api.twitter.com/oauth/authenticate?" + response.body()
                .string()
                .split("&")[0];
            Timber.i("Authentication url: " + url);
            view.loadUrl(url);
          } catch (IOException e) {
            Timber.e(e);
          }
        } else {
          view.finish();
        }
      }

      @Override @DebugLog public void onFailure(Call<ResponseBody> all, Throwable throwable) {
        Timber.e(throwable);
      }
    });
  }

  @Override public void detach() {
    view = null;
  }

  public void loginCallback(final String url) {

    final String[] params = url.replace(TwitterApp.CALLBACK_URL, "").split("&");
    Timber.i(Arrays.toString(params));

    Realm realm = Realm.getDefaultInstance();

    final TwitterServiceCredentials creds =
        realm.where(TwitterServiceCredentials.class).findFirst();
    realm.executeTransaction(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        creds.token = params[0].substring(params[0].indexOf('=') + 1);
      }
    });
    realm.close();
    RequestBody body =
        RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params[1]);
    tokenService.getAccessToken(body).enqueue(new Callback<ResponseBody>() {
      @Override @DebugLog
      public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
        if (response.code() == 200) {
          try {
            final TwitterServiceCredentials creds = parseResponse(response.body().string());
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
              @Override public void execute(Realm realm) {
                final RealmResults<TwitterServiceCredentials> oldCreds =
                    realm.where(TwitterServiceCredentials.class).findAll();
                Timber.i(oldCreds.deleteAllFromRealm() ? "Deleted creds" : "No creds exist");
                realm.copyToRealm(creds);
              }
            });

            view.addAccount(creds.userId);
            realm.close();
            view.setResultOk();
            view.finish();
          } catch (IOException e) {
            Timber.e(e);
          }
        } else {
          view.loadUrl(url);
        }
      }

      private TwitterServiceCredentials parseResponse(String response) {
        String[] pairs = response.split("&");
        String token = pairs[0].split("=")[1];
        String tokenSecret = pairs[1].split("=")[1];
        String userId = pairs[2].split("=")[1];
        String screenName = pairs[3].split("=")[1];
        String authExpires = pairs[4].split("=")[1];

        final TwitterServiceCredentials realCreds = new TwitterServiceCredentials();
        realCreds.appToken = constants.getConsumerKey();
        realCreds.appTokenSecret = constants.getSecretKey();
        realCreds.userId = userId;
        realCreds.displayName = screenName;
        realCreds.token = token;
        realCreds.tokenSecret = tokenSecret;
        realCreds.authExpires = authExpires;
        realCreds.isAuthenticated = true;
        return realCreds;
      }

      @Override @DebugLog public void onFailure(Call<ResponseBody> call, Throwable t) {
        view.setResultCanceled();
        view.finish();
      }
    });
  }
}
