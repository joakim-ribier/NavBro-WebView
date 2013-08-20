package fr.rjoakim.android.navbro;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ViewAnimator;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import fr.rjoakim.android.navbro.async.DisplayWebSourceAsyncTask;
import fr.rjoakim.android.navbro.dialog.ListUrlDialog;

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
public class MyMenu {

	private final SlidingMenu slidingMenu;
	private final View view;
	
	private final MyWebView myWebView;
	
	private final NavBroActivity navAppActivity;
	private final ViewAnimator viewAnimator;

	public MyMenu(NavBroActivity navAppActivity, MyWebView myWebView, ViewAnimator viewAnimator) {
		this.navAppActivity = navAppActivity;
		this.myWebView = myWebView;
		this.viewAnimator = viewAnimator;
		this.slidingMenu = new SlidingMenu(navAppActivity);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.my_menu_shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(navAppActivity, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.left_menu);
		
		this.view = slidingMenu.getMenu();
		
		setEventOnClickButton();
		setWebViewSettings();
	}

	private void setWebViewSettings() {
		MyWebViewPreferences myWebViewPreferences = PreferencesUtils.getMyWebViewPreferences(navAppActivity);
		setCheckBoxValue(
				R.id.LeftMenuWebViewSettingsJavaScriptEnabled,
				myWebViewPreferences.isJavaScriptEnabled(),
				PreferencesUtils.JAVA_SCRIPT_ENABLED);
		
		setCheckBoxValue(
				R.id.LeftMenuWebViewSettingsUseWideViewPort,
				myWebViewPreferences.isUseWideViewPort(),
				PreferencesUtils.USE_WIDE_VIEW_PORT);
		
		setCheckBoxValue(
				R.id.LeftMenuWebViewSettingsZoomControls,
				myWebViewPreferences.isBuiltInZoomControls(),
				PreferencesUtils.BUILT_IN_ZOOM_CONTROLS);
	}

	private void setCheckBoxValue(int resource, boolean value, final String key) {
		final CheckBox checkBox = (CheckBox) view.findViewById(resource);
		checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyWebViewPreferences myWebViewPreferences =
						PreferencesUtils.setWebViewPreferences(
								navAppActivity, key, checkBox.isChecked());
				showContent(NavBroActivity.WEB_VIEW_INDEX);
				myWebView.reload(myWebViewPreferences);
			}
		});
		checkBox.setChecked(value);
	}
	
	private void setEventOnClickButton() {
		View githubButton = view.findViewById(R.id.leftMenuViewGitHubButton);
		githubButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showContent(NavBroActivity.WEB_VIEW_INDEX);
				myWebView.loadUrl(navAppActivity.getString(R.string.github_url));
			}
		});
		
		View addUrlButton = view.findViewById(R.id.leftMenuViewAddUrlButton);
		addUrlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				navAppActivity.displaySaveNewUrlDialog(myWebView);
			}
		});
		
		View listUrlButton = view.findViewById(R.id.leftMenuViewListUrlButton);
		listUrlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new ListUrlDialog(navAppActivity) {
					@Override
					public void onSuccess(String newUrl) {
						showContent(NavBroActivity.WEB_VIEW_INDEX);
						myWebView.loadUrl(newUrl);
					}
				}.show();
			}
		});
		
		View displaySourceButton = view.findViewById(R.id.leftMenuDisplayWebSourceButton);
		displaySourceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DisplayWebSourceAsyncTask(navAppActivity, viewAnimator,
						MyMenu.this).execute(PreferencesUtils
						.getLastUrl(navAppActivity));
			}
		});
	}

	public void showOrHidden() {
		if (slidingMenu.isMenuShowing()) {
			slidingMenu.showContent();
		} else {
			slidingMenu.showMenu();
		}
	}
	
	public void showContent(int index) {
		viewAnimator.setDisplayedChild(index);
		slidingMenu.showContent();
	}
}
