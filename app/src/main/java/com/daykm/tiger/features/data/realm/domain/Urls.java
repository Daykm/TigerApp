package com.daykm.tiger.features.data.realm.domain;

import io.realm.RealmObject;

public class Urls extends RealmObject {

  private String url;
  private String display_url;
  private String expanded_url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDisplay_url() {
    return display_url;
  }

  public void setDisplay_url(String display_url) {
    this.display_url = display_url;
  }

  public String getExpanded_url() {
    return expanded_url;
  }

  public void setExpanded_url(String expanded_url) {
    this.expanded_url = expanded_url;
  }
}
