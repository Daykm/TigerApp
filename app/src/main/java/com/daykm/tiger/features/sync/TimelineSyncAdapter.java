package com.daykm.tiger.features.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import com.daykm.tiger.features.dagger.ServiceModule;
import com.daykm.tiger.features.data.realm.GsonProvider;
import com.daykm.tiger.features.data.realm.domain.Status;
import com.daykm.tiger.features.services.OAuth2Interceptor;
import com.daykm.tiger.features.services.TimelineService;
import com.daykm.tiger.features.services.TwitterApp;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class TimelineSyncAdapter extends AbstractThreadedSyncAdapter {

	TimelineService timeline;

	public TimelineSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		format.setTimeZone(TimeZone.getTimeZone("EST"));

		OkHttpClient httpBuilder = new OkHttpClient.Builder().build();

		OAuth2Interceptor interceptor = new OAuth2Interceptor();
		interceptor.isAuth = false;
		timeline =
				ServiceModule.buildService(TimelineService.class, interceptor, GsonProvider.getGson(),
						new Retrofit.Builder(), httpBuilder.newBuilder(), TwitterApp.BASE_URL_1_1);
	}

	SimpleDateFormat format = new SimpleDateFormat("MM/dd, HH:mm:ss", Locale.ENGLISH);

	@Override @DebugLog public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {

		final Realm realm = Realm.getDefaultInstance();

		RealmResults<Status> result =
				realm.where(Status.class).findAll().sort("timelineIndex", Sort.DESCENDING);
		Status status = result.size() > 0 ? result.first() : null;

		try {
			Timber.i("Last tweet at: " + (status != null ? format.format(status.created_at) : "never"));
		} catch (IllegalArgumentException e) {
			Timber.e(e);
		}

		String id =
				status != null && status.id_str != null && !status.id_str.isEmpty() ? status.id_str : null;

		realm.close();
		final Call<List<Status>> call = timeline.getTimeline(id, 20);
		call.enqueue(new Callback<List<Status>>() {
			@Override @DebugLog
			public void onResponse(Call<List<Status>> call, final Response<List<Status>> response) {
				if (response.isSuccessful()) {
					Realm realm = Realm.getDefaultInstance();
					try {
						realm.executeTransactionAsync(new Realm.Transaction() {
							@Override public void execute(Realm realm) {
								RealmResults<Status> results =
										realm.where(Status.class).findAll().sort("timelineIndex", Sort.DESCENDING);
								long index = results.size() > 0 ? results.first().timelineIndex : 0;
								for (Status status : response.body()) {
									status.timeline = "home";
									status.timelineIndex = index += 1;
									realm.insertOrUpdate(status);
								}
							}
						});
					} finally {
						if (realm != null) realm.close();
					}
				} else {
					Timber.e(response.message());
				}
			}

			@Override public void onFailure(Call<List<Status>> call, Throwable t) {
				throw new RuntimeException(t); // TODO add better handling
			}
		});
	}
}
