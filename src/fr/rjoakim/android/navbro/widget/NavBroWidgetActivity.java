package fr.rjoakim.android.navbro.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import fr.rjoakim.android.navbro.NavBroActivity;
import fr.rjoakim.android.navbro.R;
import fr.rjoakim.android.navbro.preferences.MyWidgetPreferences;
import fr.rjoakim.android.navbro.preferences.PreferencesUtils;

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
public class NavBroWidgetActivity extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		updateAppWidget(context, appWidgetManager, appWidgetIds);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	final int length = appWidgetIds.length;
		for (int i = 0; i < length; i++) {
			int appWidgetId = appWidgetIds[i];
			
			MyWidgetPreferences widgetPreferences =
					PreferencesUtils.getWidgetPreferences(context, appWidgetId);
			
			if (widgetPreferences == null) {
				updateAppWidget(context, appWidgetManager,
						appWidgetId, "invalid widget", "invalid widget");
				
			} else {
				updateAppWidget(context, appWidgetManager, appWidgetId,
						widgetPreferences.getTitleURL(), widgetPreferences.getURL());
			}
		}
    }
    
	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
			int mAppWidgetId, String titleURL, String URL) {

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_widget);
		views.setTextViewText(R.id.mainWidgetTitleTextView, format(titleURL));

		Intent navBroActivityStart = new Intent(context, NavBroActivity.class);

		navBroActivityStart.setAction(NavBroWidgetConfigurationActivity.LAUNCH_WEB_VIEW_WITH_URL);
		navBroActivityStart.putExtra(NavBroWidgetConfigurationActivity.WIDGET_KEY_URL, URL);
		
		PendingIntent mainPending = PendingIntent.getActivity(
				context, mAppWidgetId, navBroActivityStart, PendingIntent.FLAG_UPDATE_CURRENT);
		
		views.setOnClickPendingIntent(R.id.mainWidgetButtonLayout, mainPending);

		appWidgetManager.updateAppWidget(mAppWidgetId, views);
	}

	private static String format(String titleURL) {
		if (titleURL.length() > 18) {
			return titleURL.substring(0, 18) + "..";
		}
		return titleURL;
	}
}
