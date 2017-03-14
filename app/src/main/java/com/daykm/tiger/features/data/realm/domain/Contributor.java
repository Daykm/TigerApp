package com.daykm.tiger.features.data.realm.domain;

import io.realm.RealmObject;

public class Contributor extends RealmObject {
  private long id;
  private String id_str;
  private String screen_name;
  private Status status;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

  public String getScreen_name() {
    return screen_name;
  }

  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
