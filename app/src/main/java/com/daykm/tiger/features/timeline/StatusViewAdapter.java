package com.daykm.tiger.features.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.daykm.tiger.features.data.realm.domain.Status;
import com.daykm.tiger.features.timeline.view.StatusViewHolder;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class StatusViewAdapter extends RecyclerView.Adapter<StatusViewHolder>
    implements RealmChangeListener<RealmResults<Status>> {

  Context context;

  private RealmResults<Status> realmResults;

  public StatusViewAdapter(Context context, RealmResults<Status> realmResults) {
    this.context = context;
    this.realmResults = realmResults;

    setHasStableIds(true);

    realmResults.addChangeListener(this);
  }

  @Override public long getItemId(int position) {
    if (realmResults.size() > 0) {
      return realmResults.get(position).timelineIndex;
    } else {
      return -1;
    }
  }

  @Override public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new StatusViewHolder(parent);
  }

  @Override public void onBindViewHolder(StatusViewHolder holder, int position) {
    holder.bind(realmResults.get(position));
  }

  @Override public int getItemCount() {
    return 0;
  }

  @Override public void onChange(RealmResults<Status> element) {
    notifyDataSetChanged();
  }
}
