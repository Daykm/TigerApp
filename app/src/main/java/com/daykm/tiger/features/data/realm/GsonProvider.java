package com.daykm.tiger.features.data.realm;

import com.daykm.tiger.features.data.realm.adapters.RealmListTypeAdapterFactory;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.realm.RealmObject;

public class GsonProvider {

	public static Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setExclusionStrategies(new ExclusionStrategy() {
			@Override public boolean shouldSkipField(FieldAttributes f) {
				return f.getDeclaringClass().equals(RealmObject.class);
			}

			@Override public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});

		builder.registerTypeAdapterFactory(new RealmListTypeAdapterFactory());
				/*
        builder.registerTypeAdapter(new TypeToken<RealmList<IntegerWrapper>>(){}.getType(), new IntegerArray());
        */
		return builder.create();
	}
}
