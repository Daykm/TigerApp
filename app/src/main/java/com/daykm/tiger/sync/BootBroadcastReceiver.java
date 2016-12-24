package com.daykm.tiger.sync;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.daykm.tiger.R;

public class BootBroadcastReceiver  extends BroadcastReceiver{

    public static final String TAG = BootBroadcastReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        requestPeriodicSync(context);
    }


    public static void requestPeriodicSync(Context context) {
        Log.i(TAG, "Broadcast recieved");
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
