package com.daykm.tiger.features.data.realm.wrappers;

import io.realm.RealmObject;

public class StringWrapper extends RealmObject {

	private String val;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
}
