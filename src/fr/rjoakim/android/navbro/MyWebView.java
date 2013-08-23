package fr.rjoakim.android.navbro;

import fr.rjoakim.android.navbro.preferences.MyWebViewPreferences;
import fr.rjoakim.android.navbro.preferences.PreferencesUtils;
import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * Copyright 2013 Joakim Ribier
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
public class MyWebView extends WebView {

	private final NavBroActivity navAppActivity;

	@SuppressLint("SetJavaScriptEnabled")
	public MyWebView(final NavBroActivity navAppActivity) {
		super(navAppActivity);
		this.navAppActivity = navAppActivity;

		setSettings(PreferencesUtils.getMyWebViewPreferences(navAppActivity));
		
		getSettings().setLoadWithOverviewMode(true);
		getSettings().setJavaScriptEnabled(true);
		
		setWebChromeClient(new WebChromeClient() {
			
			public void onProgressChanged(WebView view, int progress) {
				navAppActivity.setTitle("...");
				navAppActivity.setProgress(progress * 100);
				if (progress == 100) {
					navAppActivity.setTitle(view.getUrl());
				}
			}
		});
		
		setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				loadUrl(url);
				return false;
			}
		});
		
		setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	}
	
	public void reload() {
		super.loadUrl("javascript:window.location.reload( true )");
	}
	
	public void reload(MyWebViewPreferences myWebViewPreferences) {
		setSettings(myWebViewPreferences);
		reload();
	}
	
	@Override
	public void loadUrl(String url) {
		super.loadUrl(url);
		PreferencesUtils.setLastUrl(navAppActivity, url);
	}
	
	public void loadUrl(MyWebViewPreferences myWebViewPreferences, String url) {
		setSettings(myWebViewPreferences);
		loadUrl(url);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setSettings(MyWebViewPreferences myWebViewPreferences) {
		getSettings().setJavaScriptEnabled(myWebViewPreferences.isJavaScriptEnabled());
		getSettings().setBuiltInZoomControls(myWebViewPreferences.isBuiltInZoomControls());
		getSettings().setUseWideViewPort(myWebViewPreferences.isUseWideViewPort());
	}
}
