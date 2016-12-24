package com.daykm.tiger.dagger;

import com.daykm.tiger.features.authentication.AuthenticationActivity;
import com.daykm.tiger.features.tweeting.TweetActivity;
import com.daykm.tiger.features.timeline.TwitterActivity;
import com.daykm.tiger.features.timeline.MentionsFragment;
import com.daykm.tiger.features.timeline.TimelineFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {
    void inject(AuthenticationActivity activity);
    void inject(TwitterActivity activity);
    void inject(TweetActivity activity);
    void inject(TimelineFragment fragment);
    void inject(MentionsFragment fragment);
}
