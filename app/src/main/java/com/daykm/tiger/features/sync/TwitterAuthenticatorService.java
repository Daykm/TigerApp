package com.daykm.tiger.features.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class TwitterAuthenticatorService extends Service {

	private TwitterAuthenticator auth;

	@Override public void onCreate() {
		auth = new TwitterAuthenticator(this);
	}

	@Nullable @Override public IBinder onBind(Intent intent) {
		return auth.getIBinder();
	}
}
