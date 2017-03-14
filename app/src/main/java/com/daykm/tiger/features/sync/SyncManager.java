package com.daykm.tiger.features.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import com.daykm.tiger.R;
import com.daykm.tiger.features.dagger.StringProvider;
import hugo.weaving.DebugLog;
import javax.inject.Inject;
import timber.log.Timber;

public class SyncManager {

  AccountManager manager;
  StringProvider stringProvider;

  @Inject public SyncManager(AccountManager manager, StringProvider stringProvider) {
    this.manager = manager;
    this.stringProvider = stringProvider;
  }

  @DebugLog public void requestSync() {
    // Pass the settings flags by inserting them in a bundle
    Bundle settingsBundle = new Bundle();
    settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
    settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

    Account account = manager.getAccountsByType(stringProvider.getString(R.string.account_type))[0];
    Timber.i("1 is syncable, 0 is not: " + ContentResolver.getIsSyncable(account,
        stringProvider.getString(R.string.authority_timeline)));
    ContentResolver.requestSync(account, stringProvider.getString(R.string.authority_timeline),
        settingsBundle);

    ContentResolver.requestSync(account, stringProvider.getString(R.string.authority_mentions),
        settingsBundle);
  }
}
