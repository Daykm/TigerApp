package com.daykm.tiger.features.data.realm.domain;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;

public class User extends RealmObject {
  public boolean contributorsEnabled;
  public String created_at;
  public boolean default_profile;
  public boolean default_profile_image;
  public String description;
  public Entities entities;
  public int favorites_count;
  public boolean follow_request_sent;
  public boolean following;
  public int followers_count;
  public int friends_count;
  public boolean geo_enabled;
  public long id;
  public String id_str;
  public boolean is_translator;
  public String lang;
  public int listed_count;
  public String location;
  public String name;
  public String profile_background_color;
  public String profile_background_image_url;
  public String profile_background_image_url_https;
  public boolean profile_background_tile;
  public String profile_banner_url;
  public String profile_image_url;
  public String profile_image_url_https;
  public String profile_link_color;
  public String profile_sidebar_fill_color;
  public String profile_text_color;
  public boolean profile_use_background_image;
  @SerializedName("protected") public boolean is_protected;
  public String screen_name;
  public boolean show_all_inline_media;
  public Status status;
  public int status_count;
  public String time_zone;
  public String url;
  public int utf_offset;
  public boolean verified;
  public String withheld_in_countries;
  public String withheld_scope;
}
