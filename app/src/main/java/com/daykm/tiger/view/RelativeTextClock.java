package com.daykm.tiger.view;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RemoteViews;
import android.widget.TextView;

@RemoteViews.RemoteView public class RelativeTextClock extends TextView {

	private boolean mAttached;
	private long startingTimeStamp;

	private final ContentObserver mFormatChangeObserver = new ContentObserver(new Handler()) {
		@Override public void onChange(boolean selfChange) {
			onTimeChanged();
		}

		@Override public void onChange(boolean selfChange, Uri uri) {
			onTimeChanged();
		}
	};

	private final Runnable mTicker = new Runnable() {
		public void run() {
			onTimeChanged();
			long now = SystemClock.uptimeMillis();
			long next = now + (1000 - now % 1000);
			getHandler().postAtTime(mTicker, next);
		}
	};

	@SuppressWarnings("UnusedDeclaration") public RelativeTextClock(Context context) {
		super(context);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @SuppressWarnings("UnusedDeclaration")
	public RelativeTextClock(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setStartingTimeStamp(long startingTimeStamp) {
		this.startingTimeStamp = startingTimeStamp;
	}

	@Override protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (!mAttached) {
			mAttached = true;
			mTicker.run();
		}
	}

	@Override protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAttached) {
			getHandler().removeCallbacks(mTicker);
			mAttached = false;
		}
	}

	private void onTimeChanged() {
		long diff = System.currentTimeMillis() / 1000L - startingTimeStamp;
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		String time = "";
		if (diffDays > 0) {
			time = Long.toString(diffDays);
		} else if (diffHours > 0) {
			time = Long.toString(diffHours);
		} else if (diffMinutes > 0) {
			time = Long.toString(diffMinutes);
		} else if (diffSeconds > 0) {
			time = Long.toString(diffSeconds);
		}

		setText(time);
		setContentDescription(time);
	}
}