package com.daykm.tiger.features.data.realm.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Created by Leks on 3/18/2016.
 */
public class IntegerIntegerMap extends TypeAdapter<Integer[]> {
	@Override public void write(JsonWriter out, Integer[] value) throws IOException {

	}

	@Override public Integer[] read(JsonReader in) throws IOException {
		return new Integer[0];
	}
}
