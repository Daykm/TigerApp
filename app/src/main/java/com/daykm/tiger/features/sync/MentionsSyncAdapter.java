package com.daykm.tiger.features.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import com.daykm.tiger.features.dagger.ServiceModule;
import com.daykm.tiger.features.data.realm.GsonProvider;
import com.daykm.tiger.features.services.OAuth2Interceptor;
import com.daykm.tiger.features.services.TimelineService;
import com.daykm.tiger.features.services.TwitterApp;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MentionsSyncAdapter extends AbstractThreadedSyncAdapter {

	public static final String TAG = MentionsSyncAdapter.class.getSimpleName();

	TimelineService mentionsService;

	public MentionsSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);

		OkHttpClient httpBuilder = new OkHttpClient.Builder().build();

		OAuth2Interceptor interceptor = new OAuth2Interceptor();

		mentionsService =
				ServiceModule.buildService(TimelineService.class, interceptor, GsonProvider.getGson(),
						new Retrofit.Builder(), httpBuilder.newBuilder(), TwitterApp.BASE_URL_1_1);
	}

	@Override public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
/*

        Realm realm = Realm.getDefaultInstance();
        TwitterServiceCredentials creds = realm.where(TwitterServiceCredentials.class).findFirst();

        Date newestDate = realm.where(Mention.class).findAll().maxDate("createdAt");

        final Call<List<Status>> call = mentionsService.getTimeline("", 300);
        call.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                if (response.isSuccessful()) {

                    Realm realm = Realm.getDefaultInstance();

                    Number currentId = Realm.getDefaultInstance().where(Mention.class).max("id");
                    int nextId = currentId != null ? currentId.intValue() : 1;
                    realm.beginTransaction();
                    int skipped = 0;
                    int created = 0;
                    for (Status status : response.body()) {
                        RealmResults<Mention> exisiting = realm.where(Mention.class).equalTo("id", status.getId()).findAll();
                        if (exisiting.size() > 0) {
                            Log.i(TAG, "TODO make this stop happening");
                            skipped++;

                        }
                    }
                    realm.commitTransaction();
                    realm.close();
                    Log.i(TAG, "Timeine entries created: " + created + ", skipped: " + skipped);
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                throw new RuntimeException(t); // TODO add better handling
            }
        });
        */

	}
}
