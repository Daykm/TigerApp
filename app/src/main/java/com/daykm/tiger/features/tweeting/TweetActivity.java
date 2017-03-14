package com.daykm.tiger.features.tweeting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.daykm.tiger.R;
import com.daykm.tiger.features.base.BaseActivity;

public class TweetActivity extends BaseActivity {

	@BindView(R.id.toolbar) Toolbar toolbar;
	public static final String INTENT_STATUS_ID = "status_id";

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
