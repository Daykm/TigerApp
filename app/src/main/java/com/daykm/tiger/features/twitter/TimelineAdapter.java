package com.daykm.tiger.features.twitter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.daykm.tiger.features.data.realm.domain.Status;
import com.daykm.tiger.features.data.repo.TimelineRepository;
import hugo.weaving.DebugLog;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;
import javax.inject.Inject;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineHolder> {

  RealmResults<Status> statuses;

  TimelineRepository repo;

  @Inject public TimelineAdapter(TimelineRepository repo) {
    this.repo = repo;
    statuses = repo.getTimeline();
    statuses.addChangeListener(createListener());
    notifyDataSetChanged();
  }

  @Override public TimelineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TimelineHolder(parent);
  }

  @Override public void onBindViewHolder(TimelineHolder holder, int position) {
    holder.bind(statuses.get(position));
  }

  @Override @DebugLog public int getItemCount() {
    return statuses.size();
  }

  private OrderedRealmCollectionChangeListener<RealmResults<Status>> createListener() {
    return new OrderedRealmCollectionChangeListener<RealmResults<Status>>() {
      @Override
      public void onChange(RealmResults<Status> collection, OrderedCollectionChangeSet changeSet) {
        // null Changes means the async query returns the first time.
        if (changeSet == null) {
          notifyDataSetChanged();
          return;
        }
        // For deletions, the adapter has to be notified in reverse order.
        OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
        for (int i = deletions.length - 1; i >= 0; i--) {
          OrderedCollectionChangeSet.Range range = deletions[i];
          notifyItemRangeRemoved(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
        for (OrderedCollectionChangeSet.Range range : insertions) {
          notifyItemRangeInserted(range.startIndex, range.length);
        }

        OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
        for (OrderedCollectionChangeSet.Range range : modifications) {
          notifyItemRangeChanged(range.startIndex, range.length);
        }
      }
    };
  }
}
