package com.daykm.tiger.features.authentication;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.daykm.tiger.features.services.TwitterApp;

public class TwitterWebViewClient extends WebViewClient {

	private Callbacks callbacks;

	public TwitterWebViewClient(Callbacks callbacks) {
		this.callbacks = callbacks;
	}

	@Override public void onPageFinished(WebView view, String url) {
		if (url.substring(0, 10).contains(TwitterApp.CALLBACK)) {
			callbacks.onCallback(url);
		}
	}

	public interface Callbacks {
		void onCallback(String url);
	}
}
