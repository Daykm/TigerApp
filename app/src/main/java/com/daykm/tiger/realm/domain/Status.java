package com.daykm.tiger.realm.domain;

import com.daykm.tiger.realm.wrappers.StringWrapper;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

/**
 * TODO: make this more complete and check deserialization performance probably
 */
public class Status extends RealmObject {
	public RealmList<Contributor> contributors;
	public RealmList<Coordinate> coordinates;
	public Retweet current_user_retweet;
	public String created_at;
	public Entities entities;
	public Entities extended_entities;
	public int favorite_count;
	public boolean favorited;
	public String filter_level;
	@PrimaryKey public long id;
	public String id_str;
	public String in_reply_to_screen_name;
	public String in_reply_to_status_id;
	public String in_reply_to_status_id_str;
	public String in_reply_to_user_id;
	public String in_reply_to_user_id_str;
	public String lang;
	public Place place;
	public boolean possibly_sensitive;
	@SerializedName("quoted_status_id") public long quotedStatusId;
	public String quoted_status_id_str;
	public Status quoted_status;
	public int retweet_count;
	public boolean retweeted;
	public Status retweeted_status;
	public String source;
	public String text;
	public boolean truncated;
	public User user;
	public boolean withheld_copyright;
	public RealmList<StringWrapper> withheld_in_countries;
	public String withheld_scope;

	public String timeline;
	public long timelineIndex;
	public boolean read;
	@Ignore private Date date;

	@Ignore private static final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

	@Ignore private static final SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);

	public Date getParsedDate() {
		if (date == null) {
			try {
				date = sf.parse(created_at);
			} catch (ParseException e) {
				Timber.e(e);
			}
		}
		return date;
	}
}
