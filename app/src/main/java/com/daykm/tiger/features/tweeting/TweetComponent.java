package com.daykm.tiger.features.tweeting;

import com.daykm.tiger.features.twitter.TwitterActivity;
import dagger.Subcomponent;

@Subcomponent public interface TweetComponent {
  void inject(TwitterActivity twitterActivity);
}
