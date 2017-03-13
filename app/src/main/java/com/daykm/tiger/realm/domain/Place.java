package com.daykm.tiger.realm.domain;

import io.realm.RealmObject;

public class Place extends RealmObject {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
