package com.daykm.tiger.features.twitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daykm.tiger.R;
import com.daykm.tiger.features.data.realm.domain.Status;
import com.daykm.tiger.features.util.DateUtil;
import com.daykm.tiger.features.view.RelativeTextClock;
import com.squareup.picasso.Picasso;

public class TimelineHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.icon) ImageView icon;
	@BindView(R.id.tweet_display_name) TextView displayName;
	@BindView(R.id.tweet_user_name) TextView userName;
	@BindView(R.id.tweet_status) TextView content;
	@BindView(R.id.tweet_timestamp) RelativeTextClock timestamp;
	@BindView(R.id.tweet_image) ImageView image;


	public TimelineHolder(ViewGroup parent) {
		super(LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet, parent, false));
		ButterKnife.bind(this, itemView);
	}

	public void bind(Status status) {
		Picasso.with(itemView.getContext()).load(status.user.profile_image_url_https).into(icon);
		displayName.setText(status.user.name);
		userName.setText(status.user.screen_name);
		content.setText(status.text);
		timestamp.setStartingTimeStamp(DateUtil.parseTwitterCreatedAt(status.created_at));
	}
}
