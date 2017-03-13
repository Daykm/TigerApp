package com.daykm.tiger.realm.wrappers;

import io.realm.RealmObject;

public class IntegerWrapper extends RealmObject {
	private Integer val;

	public IntegerWrapper() {
	}

	public IntegerWrapper(Integer val) {
		this.val = val;
	}

	public Integer getVal() {
		return val;
	}

	public void setVal(Integer val) {
		this.val = val;
	}
}
