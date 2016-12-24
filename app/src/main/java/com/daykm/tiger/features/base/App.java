package com.daykm.tiger.features.base;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;

import com.daykm.tiger.BuildConfig;
import com.daykm.tiger.dagger.DaggerServiceComponent;
import com.daykm.tiger.dagger.ServiceComponent;
import com.daykm.tiger.features.preferences.AppPreferences;
import com.daykm.tiger.realm.domain.TwitterServiceCredentials;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class App extends Application {

    private ServiceComponent component;

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }


        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm realm = Realm.getDefaultInstance();
        TwitterServiceCredentials creds = realm.where(TwitterServiceCredentials.class).findFirst();
        if (creds == null) {
            creds = new TwitterServiceCredentials();
            creds.appToken = BuildConfig.CONSUMER_KEY;
            creds.appTokenSecret = BuildConfig.SECRET_KEY;
            final TwitterServiceCredentials aliasCreds = creds;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(aliasCreds);
                }
            });
        }
        realm.close();

        component = DaggerServiceComponent.builder().build();

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(AppPreferences.APP_THEME, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


        CreateSyncAccount(this);

    }

    public static App instance() {
        return app;
    }


    public ServiceComponent getComponent() {
        return component;
    }

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.daykm.tiger";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "tiger.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";

    public static void CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
    }

}
