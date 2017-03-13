package com.daykm.tiger.realm.adapters;

import com.daykm.tiger.realm.wrappers.IntegerWrapper;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.realm.RealmList;
import java.io.IOException;

public class IntegerArray extends TypeAdapter<RealmList<IntegerWrapper>> {

	@Override public void write(JsonWriter out, RealmList<IntegerWrapper> value) throws IOException {

	}

	@Override public RealmList<IntegerWrapper> read(JsonReader in) throws IOException {
		RealmList<IntegerWrapper> list = new RealmList<>();
		in.beginArray();
		while (in.hasNext()) {
			list.add(new IntegerWrapper(in.nextInt()));
		}
		in.endArray();
		return list;
	}
}
