package com.daykm.tiger.features.twitter;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daykm.tiger.R;
import com.daykm.tiger.features.authentication.AuthActivity;
import com.daykm.tiger.features.base.App;
import com.daykm.tiger.features.base.BaseActivity;
import com.daykm.tiger.features.data.realm.domain.TwitterServiceCredentials;
import com.daykm.tiger.features.data.realm.domain.User;
import com.daykm.tiger.features.preferences.SettingsActivity;
import com.daykm.tiger.features.services.TimelineService;
import com.daykm.tiger.features.tweeting.UpdateStatusDialog;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TwitterActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	@Inject TimelineService timeline;

	@BindView(R.id.viewPager) ViewPager pager;
	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.drawer_layout) DrawerLayout drawer;
	@BindView(R.id.navigation) NavigationView navigationDrawer;
	@BindView(R.id.coordinator_layout) CoordinatorLayout layout;

	ContentPager adapter;
	ActionBarDrawerToggle toggle;

	Realm realm;

	TwitterServiceCredentials creds;

	private static final int GET_ACCOUNT_REQUEST_CODE = 9845;

	int[] titles = { R.string.timeline, R.string.mentions, R.string.activity };

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.instance().getComponent().twitterComponent().inject(this);
		realm = Realm.getDefaultInstance();
		creds = realm.where(TwitterServiceCredentials.class).findFirst();
		checkLogin();
	}

	private void checkLogin() {
		if (!creds.isAuthenticated) {
			Intent intent = new Intent(this, AuthActivity.class);
			startActivityForResult(intent, AuthActivity.REQUEST_CODE);
		} else {
			inflate();
			requestSyncPermission();
		}
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AuthActivity.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				creds = realm.where(TwitterServiceCredentials.class).findFirst();
				inflate();
			}
		}
	}

	private void requestSyncPermission() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.GET_ACCOUNTS },
					GET_ACCOUNT_REQUEST_CODE);
		} else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SYNC_SETTINGS)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[] { Manifest.permission.READ_SYNC_SETTINGS }, GET_ACCOUNT_REQUEST_CODE);
		} else {
			requestSync();
		}
	}

	@DebugLog private void requestSync() {
		// Pass the settings flags by inserting them in a bundle
		Bundle settingsBundle = new Bundle();
		settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
		settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

		AccountManager manager = AccountManager.get(this);
		Account account = manager.getAccountsByType(getString(R.string.account_type))[0];
		Timber.i("1 is syncable, 0 is not: " + ContentResolver.getIsSyncable(account,
				getString(R.string.authority_timeline)));
		ContentResolver.requestSync(account, getString(R.string.authority_timeline), settingsBundle);

		ContentResolver.requestSync(account, getString(R.string.authority_mentions), settingsBundle);
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			requestSyncPermission();
		} else {
			showPermssionSnackbar();
		}
	}

	private void showPermssionSnackbar() {
		Snackbar.make(layout, "Resistance is futile. Resign thyself", Snackbar.LENGTH_LONG)
				.setAction("Submit", new View.OnClickListener() {
					@Override public void onClick(View view) {
						requestSyncPermission();
					}
				})
				.show();
	}

	private void inflate() {
		setContentView(R.layout.activity_twitter);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		drawer.setStatusBarBackground(R.color.primary_dark);
		//navigationDrawer.addHeaderView();
		//navigationDrawer.setNavigationItemSelectedListener(this);

		setTitle(titles[0]);
		toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.access_open_nav,
				R.string.access_close_nav);
		drawer.addDrawerListener(toggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		toggle.syncState();

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(final View view) {
				UpdateStatusDialog dialog = UpdateStatusDialog.newInstance(timeline);
				dialog.show(getSupportFragmentManager(), null);
			}
		});

		adapter = new ContentPager(getSupportFragmentManager());
		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override public void onPageSelected(int position) {
				setTitle(titles[position]);
			}

			@Override public void onPageScrollStateChanged(int state) {

			}
		});

		pager.setAdapter(adapter);
		timeline.getUser(creds.userId, null).enqueue(new TimelineCallback());
	}

	public class TimelineCallback implements Callback<User> {
		@Override @DebugLog public void onResponse(Call<User> call, Response<User> response) {
			if (response.code() == 200) {
			}
		}

		@Override @DebugLog public void onFailure(Call<User> call, Throwable t) {
			Snackbar.make(layout, t.getMessage(), Snackbar.LENGTH_LONG)
					.setAction(R.string.action_quit, new View.OnClickListener() {
						@Override public void onClick(View v) {
							finish();
						}
					})
					.show(); // Don’t forget to show!
		}
	}

	@Override public boolean onNavigationItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.nav_timeline:
				pager.setCurrentItem(0);
				drawer.closeDrawers();
				break;
			case R.id.nav_metions:
				pager.setCurrentItem(1);
				drawer.closeDrawers();
				break;
			case R.id.nav_settings:
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
		}
		return false;
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_twitter, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		if (realm != null) realm.close();
	}
}
