package com.daykm.tiger.realm.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.realm.RealmList;
import io.realm.RealmObject;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class RealmListTypeAdapterFactory implements TypeAdapterFactory {

	@Override public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		if (type.getRawType() != RealmList.class || !(type.getType() instanceof ParameterizedType)) {
			return null;
		}

		final Type elementType = ((ParameterizedType) type.getType()).getActualTypeArguments()[0];
		final TypeAdapter<?> elementAdapter = gson.getAdapter(TypeToken.get(elementType));

		return new ListTypeAdapter(elementAdapter);
	}

	private class ListTypeAdapter<E extends RealmObject> extends TypeAdapter<RealmList<E>> {

		private TypeAdapter<E> elementAdapter;

		public ListTypeAdapter(TypeAdapter<E> elementAdapter) {
			this.elementAdapter = elementAdapter;
		}

		@Override public void write(JsonWriter out, RealmList<E> value) throws IOException { //
			// TODO how do
		}

		@Override public RealmList<E> read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			RealmList<E> list = new RealmList<>();
			in.beginArray();
			while (in.hasNext()) {
				E element = elementAdapter.read(in);
				list.add(element);
			}
			in.endArray();

			return list;
		}
	}
}