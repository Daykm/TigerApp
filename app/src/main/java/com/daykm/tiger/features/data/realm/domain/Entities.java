package com.daykm.tiger.features.data.realm.domain;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Entities extends RealmObject {

  public RealmList<Hashtag> hashtags;
  public RealmList<Symbol> symbols;
  public RealmList<Urls> urls;
  public RealmList<Media> media;
}
