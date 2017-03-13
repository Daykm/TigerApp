package com.daykm.tiger.features.tweeting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.daykm.tiger.R;
import com.daykm.tiger.features.services.TimelineService;
import com.daykm.tiger.features.twitter.TwitterActivity;
import hugo.weaving.DebugLog;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UpdateStatusDialog extends DialogFragment {

	@BindView(R.id.tweet_edit) EditText tweet;

	@BindView(R.id.lookup) RecyclerView lookup;

	@BindView(R.id.add_media) ImageButton addMediaButton;

	@BindView(R.id.at) Button atButton;

	@BindView(R.id.hashtag) Button hashtagButton;

	@BindView(R.id.character_counter) Button tweetButton;

	private TimelineService service;

	public static UpdateStatusDialog newInstance(TimelineService service) {
		UpdateStatusDialog dialog = new UpdateStatusDialog();
		dialog.service = service;
		return dialog;
	}

	@NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View fragmentView = inflater.inflate(R.layout.fragment_update_status_dialog, null);

		ButterKnife.bind(this, fragmentView);

		lookup.setAdapter(new LookupAdapter(getContext()));

		// TODO next android support library release will fix wrap content https://code.google.com/p/android/issues/detail?id=74772#c42
		// TODO fixed, remove hack layout manager
		lookup.setLayoutManager(
				new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

		lookup.setVisibility(View.VISIBLE);

		tweet.addTextChangedListener(new TextWatcher() {
			//TODO figure out this mess
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				tweetButton.setText(Integer.toString(130 - s.length()));
			}

			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override public void afterTextChanged(Editable s) {

			}
		});

		return new AlertDialog.Builder(getActivity()).setView(fragmentView).create();
	}

	@OnClick(R.id.character_counter) void onTweet() {
		String text = tweet.getText().toString();

		service.postStatus(text).enqueue(new Callback<Void>() {
			// TODO make sure this doesn't leak
			final TwitterActivity activityRef = (TwitterActivity) getActivity();

			@Override @DebugLog public void onResponse(Call<Void> call, Response<Void> response) {
				if (response.code() == 200) {
					Snackbar.make(activityRef.findViewById(R.id.fab), "Tweet sent successfully",
							Snackbar.LENGTH_SHORT).show();
				} else {
					try {
						Snackbar.make(activityRef.findViewById(R.id.fab), response.errorBody().string(),
								Snackbar.LENGTH_SHORT).show();
					} catch (IOException e) {
						Timber.e(e);
					}
				}
			}

			@Override public void onFailure(Call<Void> call, Throwable t) {

			}
		});
		dismiss();
	}

	private static class LookupAdapter extends RecyclerView.Adapter<UserView> {

		Context context;

		public LookupAdapter(Context context) {
			this.context = context;
		}

		@Override public UserView onCreateViewHolder(ViewGroup parent, int viewType) {

			return new UserView(
					LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_user, parent, false));
		}

		@Override public void onBindViewHolder(UserView holder, int position) {
			holder.icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tiger));
			holder.userName.setText("@CuteTigerAccount");
		}

		@Override public int getItemCount() {
			return 10;
		}
	}

	public static class UserView extends RecyclerView.ViewHolder {

		@BindView(R.id.icon) ImageView icon;
		@BindView(R.id.tweet_user_name) TextView userName;

		public UserView(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
