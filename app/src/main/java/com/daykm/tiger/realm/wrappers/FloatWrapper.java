package com.daykm.tiger.realm.wrappers;

import io.realm.RealmObject;

public class FloatWrapper extends RealmObject {
	private Float val;

	public Float getVal() {
		return val;
	}

	public void setVal(Float val) {
		this.val = val;
	}
}
