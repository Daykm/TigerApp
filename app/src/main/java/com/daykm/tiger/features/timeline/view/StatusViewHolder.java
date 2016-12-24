package com.daykm.tiger.features.timeline.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daykm.tiger.R;
import com.daykm.tiger.realm.domain.Status;
import com.daykm.tiger.view.RelativeTextClock;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StatusViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon)
    public ImageView icon;

    @BindView(R.id.tweet_display_name)
    public TextView displayName;

    @BindView(R.id.tweet_user_name)
    public TextView userName;

    @BindView(R.id.tweet_status)
    public TextView tweetStatus;

    @BindView(R.id.tweet_timestamp)
    public RelativeTextClock timestamp;

    @BindView(R.id.tweet_image)
    public ImageView tweetImage;

    public StatusViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet, parent, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(Status status) {
        Timber.i("Status get");
    }
}
