package com.daykm.tiger.features.timeline;

import com.daykm.tiger.features.twitter.TimelineView;
import dagger.Subcomponent;

@Subcomponent public interface TimelineComponent {

  void inject(TimelineView view);
}
