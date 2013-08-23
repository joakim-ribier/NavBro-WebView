package fr.rjoakim.android.navbro;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ViewAnimator;

import com.google.common.base.Strings;

import fr.rjoakim.android.navbro.dialog.SaveNewUrlDialog;
import fr.rjoakim.android.navbro.preferences.PreferencesUtils;
import fr.rjoakim.android.navbro.widget.NavBroWidgetConfigurationActivity;

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
public class NavBroActivity extends Activity {

	public final static int WEB_VIEW_INDEX = 0;
	public final static int WEB_SOURCE_INDEX = 1;
	
	private MyWebView myWebView;
	private MyMenu myMenu;
	
	private ViewAnimator viewAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

		setContentView(
				getLayoutInflater().inflate(R.layout.main, null));

		this.viewAnimator = (ViewAnimator) findViewById(R.id.mainViewAnimatorLayout);

		this.myWebView = new MyWebView(this);
		this.myMenu = new MyMenu(this, myWebView, viewAnimator);

		viewAnimator.addView(myWebView);
		viewAnimator.addView(
				getLayoutInflater().inflate(R.layout.web_source, null));
		
		if(!startActivityFromWidget(getIntent())){
			startActivity();
		}
	}

	private void startActivity() {
		String loadUrl = PreferencesUtils.getLastUrl(this);
		if (loadUrl == null) {
			displaySaveNewUrlDialog(myWebView);
		} else {
			myWebView.loadUrl(loadUrl);
		}
	}

	public void displaySaveNewUrlDialog(final MyWebView myWebView) {
		new SaveNewUrlDialog(NavBroActivity.this) {
			@Override
			public void onSuccess(String newUrl) {
				myMenu.showContent(WEB_VIEW_INDEX);
				myWebView.loadUrl(newUrl);
				PreferencesUtils.saveNewUrl(NavBroActivity.this, newUrl);
			}
		}.show();
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.top_menu, menu);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		} else {
			final MenuItem findItem = menu.findItem(R.id.top_menu_left_menu);
			if (findItem != null) {
				findItem.setVisible(true);
			}
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			myMenu.showOrHidden();
			break;
		case R.id.top_menu_left_menu:
			myMenu.showOrHidden();
			break;
		case R.id.top_menu_reload:
			myMenu.showContent(WEB_VIEW_INDEX);
			myWebView.reload();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		startActivityFromWidget(intent);
	}

	private boolean startActivityFromWidget(Intent intent) {
		if (intent.getAction().equals(
				NavBroWidgetConfigurationActivity.LAUNCH_WEB_VIEW_WITH_URL)) {
			
			String URL = intent.getStringExtra(
					NavBroWidgetConfigurationActivity.WIDGET_KEY_URL);
			if (!Strings.isNullOrEmpty(URL)) {
				myMenu.showContent(WEB_VIEW_INDEX);
				myWebView.loadUrl(URL);
				return true;
			}
		}
		return false;
	}
}
