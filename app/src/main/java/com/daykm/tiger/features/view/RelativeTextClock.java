package com.daykm.tiger.features.view;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.RemoteViews;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

@RemoteViews.RemoteView public class RelativeTextClock extends AppCompatTextView {

  private boolean mAttached;
  private LocalDateTime timestamp;
  private final Runnable mTicker = new Runnable() {
    public void run() {
      updateTime();
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

  public void setStartingTimeStamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
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

  private void updateTime() {
    Duration duration = Duration.between(LocalDateTime.now(), timestamp);

    long diffSeconds = duration.getSeconds();
    long diffMinutes = duration.toMinutes();
    long diffHours = duration.toHours();
    long diffDays = duration.toDays();
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
