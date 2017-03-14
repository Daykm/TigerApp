package com.daykm.tiger.features.twitter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.daykm.tiger.features.base.App;
import javax.inject.Inject;

public class TimelineView extends RecyclerView {

	public TimelineView(Context context) {
		super(context);
		init();
	}

	public TimelineView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TimelineView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Inject TimelineAdapter adapter;

	void init() {
		App.instance().getComponent().inject(this);
		setLayoutManager(new LinearLayoutManager(getContext()));
		setAdapter(adapter);
	}
}
