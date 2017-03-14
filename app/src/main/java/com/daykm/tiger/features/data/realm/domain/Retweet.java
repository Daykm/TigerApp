package com.daykm.tiger.features.data.realm.domain;

import io.realm.RealmObject;

public class Retweet extends RealmObject {
	private long id;
	private String id_str;

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
}
