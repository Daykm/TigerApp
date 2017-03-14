package com.daykm.tiger.features.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daykm.tiger.R;

public class SettingsActivity extends AppCompatActivity
		implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

	@BindView(R.id.toolbar) Toolbar toolbar;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, new SettingsFragment())
				.commit();
	}

	/**
	 * Override the back button to support fragment backstack navigation
	 */
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return false;
	}

	@Override
	public boolean onPreferenceStartFragment(PreferenceFragmentCompat preferenceFragmentCompat,
			Preference preference) {
		setTitle(preference.getTitle());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SettingsFragment fragment = new SettingsFragment();
		Bundle args = new Bundle();
		args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, preference.getKey());
		fragment.setArguments(args);
		ft.replace(R.id.content, fragment, preference.getKey());
		ft.addToBackStack(preference.getKey());
		ft.commit();
		return true;
	}

	public static class SettingsFragment extends PreferenceFragmentCompat {
		@Override public void onCreatePreferences(Bundle bundle, String rootKey) {
			setPreferencesFromResource(R.xml.pref, rootKey);
		}
	}

	int localNightMode = 0;

	public void toggleDayNight() {
		if (localNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
			getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
			getDelegate().applyDayNight();
			localNightMode = AppCompatDelegate.MODE_NIGHT_NO;
			recreate();
		} else {
			getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
			getDelegate().applyDayNight();
			localNightMode = AppCompatDelegate.MODE_NIGHT_YES;
			recreate();
		}
	}
}