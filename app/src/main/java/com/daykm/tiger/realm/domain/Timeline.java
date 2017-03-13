package com.daykm.tiger.realm.domain;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Timeline extends RealmObject {
	@Ignore public static final String HOME = "home";
	@Ignore public static final String MENTIONS = "mentions";
	@PrimaryKey public String name;
	public RealmList<Status> timeline;
}
