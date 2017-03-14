package com.daykm.tiger.features.mentions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daykm.tiger.R;
import com.daykm.tiger.features.data.realm.domain.TwitterServiceCredentials;
import com.daykm.tiger.features.services.TimelineService;
import com.daykm.tiger.features.timeline.StatusViewAdapter;

public class MentionsFragment extends Fragment {

  StatusViewAdapter adapter;
  TimelineService service;

  TwitterServiceCredentials user;

  public static MentionsFragment newInstance(TimelineService timelineService,
      TwitterServiceCredentials user) {
    MentionsFragment fragment = new MentionsFragment();
    Bundle args = new Bundle();
    fragment.service = timelineService;
    fragment.setArguments(args);
    fragment.user = user;

    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }

    //adapter = new StatusViewAdapter(getContext());

        /*
        service.getMentions(user.userId, null, 800).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                if(response.code() == 200) {
                    adapter.add(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {

            }
        });
        */
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_mentions, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mentions);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
    // Inflate the layout for this fragment
    return view;
  }
}
