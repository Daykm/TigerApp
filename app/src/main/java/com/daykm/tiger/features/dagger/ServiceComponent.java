package com.daykm.tiger.features.dagger;

import com.daykm.tiger.features.authentication.AuthActivity;
import com.daykm.tiger.features.timeline.MentionsFragment;
import com.daykm.tiger.features.timeline.TimelineFragment;
import com.daykm.tiger.features.tweeting.TweetActivity;
import com.daykm.tiger.features.twitter.TimelineView;
import com.daykm.tiger.features.twitter.TwitterActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { ServiceModule.class }) public interface ServiceComponent {
	void inject(AuthActivity activity);

	void inject(TwitterActivity activity);

	void inject(TweetActivity activity);

	void inject(TimelineFragment fragment);

	void inject(MentionsFragment fragment);

	void inject(TimelineView timelineView);
}
