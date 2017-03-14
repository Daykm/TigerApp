package com.daykm.tiger.features.data.repo;

import com.daykm.tiger.features.data.realm.domain.Status;
import io.realm.Realm;
import io.realm.RealmResults;
import javax.inject.Inject;

public class TimelineRepository {

  Realm realm = Realm.getDefaultInstance();

  @Inject TimelineRepository() {
  }

  public RealmResults<Status> getTimeline() {
    return realm.where(Status.class).findAll();
  }
}
