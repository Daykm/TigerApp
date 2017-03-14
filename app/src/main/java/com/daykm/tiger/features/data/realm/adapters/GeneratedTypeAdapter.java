package com.daykm.tiger.features.data.realm.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class GeneratedTypeAdapter<T> extends TypeAdapter<T> {
  public HashMap<String, Method> getters;
  public HashMap<String, Method> setters;
  public List<String> fields;

  @Override public void write(JsonWriter out, T value) throws IOException {
    for (String field : fields) {

      //out.name(field).value(getters.get(field).invoke(value, null));
    }
  }

  @Override public T read(JsonReader in) throws IOException {
    return null;
  }
}
