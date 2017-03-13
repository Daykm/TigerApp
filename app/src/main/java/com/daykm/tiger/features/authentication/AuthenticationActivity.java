package com.daykm.tiger.features.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.webkit.WebView;
import com.daykm.tiger.BuildConfig;
import com.daykm.tiger.R;
import com.daykm.tiger.features.base.BaseActivity;
import com.daykm.tiger.realm.domain.TwitterServiceCredentials;
import com.daykm.tiger.services.AccessTokenService;
import com.daykm.tiger.services.AuthenticationService;
import com.daykm.tiger.services.TwitterApp;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import java.io.IOException;
import java.util.Arrays;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class AuthenticationActivity extends BaseActivity implements TwitterWebViewClient.Callbacks {
	@Inject AuthenticationService authService;

	@Inject AccessTokenService tokenService;

	public static final short REQUEST_CODE = 3333;

	public WebView webView;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		component.inject(this);

		setContentView(R.layout.activity_authentication);

		final WebView webView = (WebView) findViewById(R.id.webview);
		webView.clearCache(true);
				/*
				webView.requestFocus(View.FOCUS_DOWN);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });
        */

		webView.setWebViewClient(new TwitterWebViewClient(this));

		authService.getRequestToken().enqueue(new Callback<ResponseBody>() {
			@Override @DebugLog
			public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
				if (response.code() == 200) {
					try {
						String url = "https://api.twitter.com/oauth/authenticate?" + response.body()
								.string()
								.split("&")[0];
						Timber.i("Authentication url: " + url);
						webView.loadUrl(url);
					} catch (IOException e) {
						Timber.e(e);
					}
				} else {
					finish();
				}
			}

			@Override @DebugLog public void onFailure(Call<ResponseBody> all, Throwable throwable) {
				Timber.e(throwable);
			}
		});
	}

	@Override @DebugLog public void onCallback(final String url) {
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

						Realm realm = Realm.getDefaultInstance();
						final String responseString = response.body().string();

						// TODO make this cleaner and faster probably
						String[] pairs = responseString.split("&");
						String token = pairs[0].split("=")[1];
						String tokenSecret = pairs[1].split("=")[1];
						String userId = pairs[2].split("=")[1];
						String screenName = pairs[3].split("=")[1];
						String authExpires = pairs[4].split("=")[1];
						final TwitterServiceCredentials tempCreds =
								realm.where(TwitterServiceCredentials.class).findFirst();

						final TwitterServiceCredentials realCreds = new TwitterServiceCredentials();
						realCreds.appToken = BuildConfig.CONSUMER_KEY;
						realCreds.appTokenSecret = BuildConfig.SECRET_KEY;
						realCreds.userId = userId;
						realCreds.displayName = screenName;
						realCreds.token = token;
						realCreds.tokenSecret = tokenSecret;
						realCreds.authExpires = authExpires;
						realCreds.isAuthenticated = true;
						realm.executeTransaction(new Realm.Transaction() {
							@Override public void execute(Realm realm) {
								tempCreds.deleteFromRealm();
								realm.copyToRealm(realCreds);
							}
						});
						AccountManager.get(AuthenticationActivity.this)
								.addAccountExplicitly(new Account(userId, getString(R.string.account_type)), null,
										null);

						realm.close();
						setResult(RESULT_OK);
						finish();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					webView.loadUrl(url);
				}
			}

			@Override @DebugLog public void onFailure(Call<ResponseBody> call, Throwable t) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
