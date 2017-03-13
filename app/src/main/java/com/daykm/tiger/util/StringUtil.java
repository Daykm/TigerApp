package com.daykm.tiger.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

public class StringUtil {

	public static boolean containsIgnoreCase(String src, String what) {
		final int length = what.length();
		if (length == 0) return true; // Empty string is contained

		final char firstLo = Character.toLowerCase(what.charAt(0));
		final char firstUp = Character.toUpperCase(what.charAt(0));

		for (int i = src.length() - length; i >= 0; i--) {
			// Quick check before calling the more expensive regionMatches()
			// method:
			final char ch = src.charAt(i);
			if (ch != firstLo && ch != firstUp) continue;

			if (src.regionMatches(true, i, what, 0, length)) return true;
		}

		return false;
	}

	// Tue Feb 02 19:15:17 +0000 2016
	private static final String TWITTER = "EEE MMM dd HH:mm:ss Z yyyy";
	private static final SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);

	public static Date getTwitterDate(String date) {
		sf.setLenient(false);
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			Timber.e(e);
			return null;
		}
	}

	public static String getTimeString(String timestamp) {
		Date statusDate = getTwitterDate(timestamp);
		long diffInSeconds = (System.currentTimeMillis() - statusDate.getTime()) / 1000;
		if (diffInSeconds < 60) {
			return Long.toString(diffInSeconds) + "s";
		}
		if (diffInSeconds < 60 * 60) {
			return Long.toString(diffInSeconds / 60) + "m";
		}
		if (diffInSeconds < 60 * 60 * 24) {
			return Long.toString(diffInSeconds / (60 * 60)) + "h";
		}
		return Long.toString(diffInSeconds / (60 * 60 * 24)) + "d";
	}
}
