package fr.rjoakim.android.navbro;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.common.collect.Lists;

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
public class PreferencesUtils {
	
	private static final String PREFS_FILE = "myprefs";
	private static final String LAST_URL = "my_last_url";
	private static final String COUNT_URL = "my_count_url";
	private static final String MY_URL = "my_url";

	public static final String BUILT_IN_ZOOM_CONTROLS = "BuiltInZoomControls";
	public static final String JAVA_SCRIPT_ENABLED = "JavaScriptEnabled";
	public static final String USE_WIDE_VIEW_PORT = "UseWideViewPort";

	public static String getLastUrl(Activity activity) {
		SharedPreferences sharedPreferences = getSharePreferences(activity);
		return sharedPreferences.getString(LAST_URL, null);
	}

	private static SharedPreferences getSharePreferences(Activity activity) {
		return activity.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
	}
	
	public static void setLastUrl(Activity activity, String url) {
		SharedPreferences sharedPreferences = getSharePreferences(activity);
		
		Editor edit = sharedPreferences.edit();
		edit.putString(LAST_URL, url);
		edit.commit();
	}
	
	public static void saveNewUrl(NavBroActivity navAppActivity, String newUrl) {
		SharedPreferences sharedPreferences = getSharePreferences(navAppActivity);
		int id = sharedPreferences.getInt(COUNT_URL, 0) + 1;
		
		Editor edit = sharedPreferences.edit();
		edit.putInt(COUNT_URL, id);
		edit.putString(MY_URL + "_" + id, newUrl);
		edit.commit();
	}
	
	public static List<String> listMyUrl(NavBroActivity navAppActivity) {
		SharedPreferences sharedPreferences = getSharePreferences(navAppActivity);
		int count = sharedPreferences.getInt(COUNT_URL, -1);
		List<String> myUrls = Lists.newArrayList();
		if (count != -1) {
			for (int cpt = 1; cpt <= count; cpt++) { 
				String key = MY_URL + "_" + cpt;
				String myUrl = sharedPreferences.getString(key, null);
				if (myUrl != null) {
					myUrls.add(myUrl);
				}
			}
		}
		return myUrls;
	}
	
	public static void setMyNewUrls(NavBroActivity navAppActivity,
			List<String> listMyUrl) {
		
		copyAndInit(navAppActivity);
		
		SharedPreferences sharedPreferences = getSharePreferences(navAppActivity);
		
		Editor edit = sharedPreferences.edit();
		int size = listMyUrl.size();
		edit.putInt(COUNT_URL, size);
		for (int cpt = 1; cpt <= size; cpt++) {
			String key = MY_URL + "_" + cpt; 
			edit.putString(key, listMyUrl.get(cpt-1));
		}
		edit.commit();
	}

	private static void copyAndInit(NavBroActivity navAppActivity) {
		String lastUrl = getLastUrl(navAppActivity);
		MyWebViewPreferences myWebViewPreferences = getMyWebViewPreferences(navAppActivity);
		
		SharedPreferences sharedPreferences = getSharePreferences(navAppActivity);
		Editor edit = sharedPreferences.edit();
		edit.clear();
		edit.commit();
		
		edit.putString(LAST_URL, lastUrl);
		edit.putBoolean(BUILT_IN_ZOOM_CONTROLS, myWebViewPreferences.isBuiltInZoomControls());
		edit.putBoolean(JAVA_SCRIPT_ENABLED, myWebViewPreferences.isJavaScriptEnabled());
		edit.putBoolean(USE_WIDE_VIEW_PORT, myWebViewPreferences.isUseWideViewPort());
		
		edit.commit();
	}
	
	public static MyWebViewPreferences getMyWebViewPreferences(NavBroActivity navAppActivity) {
		SharedPreferences sharedPreferences = getSharePreferences(navAppActivity);
		boolean javaScriptEnabled = sharedPreferences.getBoolean(JAVA_SCRIPT_ENABLED, true);
		boolean builtInZoomControls = sharedPreferences.getBoolean(BUILT_IN_ZOOM_CONTROLS, false);
		boolean userWideViewPort = sharedPreferences.getBoolean(USE_WIDE_VIEW_PORT, false);
		return new MyWebViewPreferences(
				javaScriptEnabled,
				builtInZoomControls,
				userWideViewPort);
	}

	public static MyWebViewPreferences setWebViewPreferences(NavBroActivity navAppActivity,
			String key, boolean checked) {

		SharedPreferences sharedPreferences = getSharePreferences(navAppActivity);
		
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, checked);
		edit.commit();
		
		return getMyWebViewPreferences(navAppActivity);
	}
}
