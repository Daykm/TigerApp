package com.daykm.tiger.features.twitter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.daykm.tiger.features.mentions.MentionsFragment;
import com.daykm.tiger.features.timeline.TimelineFragment;
import hugo.weaving.DebugLog;
import timber.log.Timber;

public class ContentPager extends FragmentPagerAdapter {

	TimelineFragment timelineFragment;
	MentionsFragment mentionsFragment;

	public ContentPager(FragmentManager fm) {
		super(fm);
	}

	@Override @DebugLog public Fragment getItem(int position) {
		switch (position) {
			case 0:
				if (timelineFragment == null) {
					timelineFragment = new TimelineFragment();
				}
				return timelineFragment;
			case 1:
				if (mentionsFragment == null) {
					mentionsFragment = new MentionsFragment();
				}
				return mentionsFragment;
			default:
				Timber.e("Invalid adapter position");
				return null;
		}
	}

	@Override public int getCount() {
		return 2;
	}
}
