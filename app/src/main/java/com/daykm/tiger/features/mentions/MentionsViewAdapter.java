package com.daykm.tiger.features.mentions;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MentionsViewAdapter extends RecyclerView.Adapter<MentionsViewAdapter.MentionViewHolder> {


    @Override
    public MentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MentionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MentionViewHolder extends RecyclerView.ViewHolder {

        public MentionViewHolder(View itemView) {
            super(itemView);
        }
    }
}
