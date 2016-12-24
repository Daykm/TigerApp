package com.daykm.tiger.features.timeline.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.daykm.tiger.features.timeline.StatusViewAdapter;
import com.daykm.tiger.realm.domain.Status;

import io.realm.Realm;
import io.realm.Sort;

public class TimelineView extends FrameLayout {
    public TimelineView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TimelineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimelineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private StatusViewAdapter adapter;
    private Realm realm;

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        recyclerView = new RecyclerView(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar = new ProgressBar(context, attrs, defStyleAttr, defStyleRes);
        } else {
            progressBar = new ProgressBar(context, attrs, defStyleAttr);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(params);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);
        realm = Realm.getDefaultInstance();
        adapter = new StatusViewAdapter(context, realm.where(Status.class).findAllSorted("timelineIndex", Sort.DESCENDING));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        addView(recyclerView);
        addView(progressBar);
    }
}
