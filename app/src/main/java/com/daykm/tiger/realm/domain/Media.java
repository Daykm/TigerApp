package com.daykm.tiger.realm.domain;

import io.realm.RealmObject;

public class Media extends RealmObject {
	enum Type {
		PHOTO("Photo"), MULTIPHOTO("multi photo"), GIFS("animated gifs"), VIDEO("video");

		private String type;

		Type(String type) {
			this.type = type;
		}

		public boolean equals(String type) {
			return (type == null) ? false : this.type.equals(type);
		}

		public String toString() {
			return type;
		}
	}

	public static final String PHOTO = "PHOTO";

	public long id;
	public String id_str;
	public String media_url;
	public String media_url_https;
	public String url;
	public String display_url;
	public String expanded_url;
	public Sizes sizes;
	public String type;
}
