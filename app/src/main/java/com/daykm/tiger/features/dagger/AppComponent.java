package com.daykm.tiger.features.dagger;

import com.daykm.tiger.features.authentication.AuthComponent;
import com.daykm.tiger.features.timeline.TimelineComponent;
import com.daykm.tiger.features.tweeting.TweetComponent;
import dagger.Component;

@Component(modules = AppModule.class) @AppScope public interface AppComponent {

	StringProvider stringProvider();

	TimelineComponent timelineComponent();

	TweetComponent twitterComponent();

	AuthComponent authComponent();
}
