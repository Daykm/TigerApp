package com.daykm.tiger.realm.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TwitterServiceCredentials extends RealmObject {

	@PrimaryKey public String userId;
	public String displayName;
	public String token;
	public String tokenSecret;
	public String appToken;
	public String appTokenSecret;
	public String authExpires;
	public boolean isAuthenticated;
}
