package com.daykm.tiger.features.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.webkit.WebView;
import com.daykm.tiger.R;
import com.daykm.tiger.features.base.App;
import com.daykm.tiger.features.base.BaseActivity;
import hugo.weaving.DebugLog;
import javax.inject.Inject;

public class AuthActivity extends BaseActivity
		implements TwitterWebViewClient.Callbacks, AuthContract.View {

	@Inject AuthPresenter presenter;

	public static final short REQUEST_CODE = 3333;

	public WebView webView;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.instance().getComponent().authComponent().inject(this);
		presenter.attach(this);
		setContentView(R.layout.activity_authentication);
		webView = (WebView) findViewById(R.id.webview);
		webView.clearCache(true);
		webView.setWebViewClient(new TwitterWebViewClient(this));
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		presenter.detach();
	}

	@Override @DebugLog public void onCallback(final String url) {
		presenter.loginCallback(url);
	}

	@Override public void loadUrl(String url) {
		webView.loadUrl(url);
	}

	@Override public void addAccount(String userId) {
		AccountManager.get(AuthActivity.this)
				.addAccountExplicitly(new Account(userId, getString(R.string.account_type)), null, null);
	}

	@Override public void setResultOk() {
		setResult(RESULT_OK);
	}

	@Override public void setResultCanceled() {
		setResult(RESULT_CANCELED);
	}
}
