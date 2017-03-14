package com.daykm.tiger.features.timeline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daykm.tiger.features.twitter.TimelineView;

public class TimelineFragment extends Fragment {

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return new TimelineView(container.getContext());
  }
}
