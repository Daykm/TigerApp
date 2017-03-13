package com.daykm.tiger.features.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import hugo.weaving.DebugLog;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override @DebugLog public void onReceive(Context context, Intent intent) {
		requestPeriodicSync(context);
	}

	public static void requestPeriodicSync(Context context) {
	}
				/*
				// TODO because fuck
        int freq = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("sync_frequency", "5"));

        ContentResolver.addPeriodicSync(
                AccountManager.get(context).getAccountsByType(context.getString(R.string.account_type))[0],
                context.getString(R.string.authority),
                Bundle.EMPTY, freq*60);
    }
    */
}
