package com.daykm.tiger.features.dagger;

import android.content.Context;
import android.support.annotation.StringRes;
import hugo.weaving.DebugLog;
import javax.inject.Inject;

public class StringProvider {

	Context context;

	@Inject @DebugLog public StringProvider(Context context) {
		this.context = context;
	}

	public String getString(@StringRes int id) {
		return context.getString(id);
	}

	public String getString(@StringRes int id, Object... args) {
		return context.getString(id, args);
	}
}
