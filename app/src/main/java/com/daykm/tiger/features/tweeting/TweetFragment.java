package com.daykm.tiger.features.tweeting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daykm.tiger.R;
import com.daykm.tiger.features.data.realm.domain.Status;
import io.realm.Realm;

public class TweetFragment extends Fragment {

  private static final String ID = "id";

  public static TweetFragment instance(long statusId) {
    TweetFragment fragment = new TweetFragment();
    Bundle bundle = new Bundle();
    bundle.putLong(ID, statusId);
    fragment.setArguments(bundle);
    return fragment;
  }

  Status status;
  Realm realm;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    realm = Realm.getDefaultInstance();
    status = realm.where(Status.class).equalTo("id", getArguments().getLong(ID)).findFirst();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_tweet, container, false);
    return view;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (realm != null) {
      realm.close();
    }
  }
}
