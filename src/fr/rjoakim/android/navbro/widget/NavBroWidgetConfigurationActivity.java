package fr.rjoakim.android.navbro.widget;

import java.util.List;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;

import fr.rjoakim.android.navbro.R;
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
public class NavBroWidgetConfigurationActivity extends Activity {
	
	public static final String LAUNCH_WEB_VIEW_WITH_URL = "LAUNCH_WEB_VIEW_WITH_URL";
	public static final String WIDGET_KEY_URL = "widgetKeyURL";

	
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_configuration_widget);
        setResult(RESULT_CANCELED);
        
        Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
		
		addMyURLsWidget();
		AddEventButtons();
	}

	private void addMyURLsWidget() {
		RadioGroup radioGroup = (RadioGroup)
				findViewById(R.id.mainConfigurationWidgetURLsRadioGroup);
		
		List<String> listMyURLs = PreferencesUtils.listMyURL(this);
		int id = 0;
		for (String myURL: listMyURLs) {
			RadioButton radioButton = (RadioButton)
					getLayoutInflater().inflate(R.layout.radio_button_widget_url, null);
			radioButton.setId(id);
			radioButton.setText(myURL);
			radioGroup.addView(radioButton);
			id++;
		}
	}
	
	private void AddEventButtons() {
		View addButton = findViewById(R.id.mainConfigurationWidgetAddButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView textView = (TextView) findViewById(R.id.mainConfigurationWidgetTitleTextView);
				if (textView.getText() != null &&
						!Strings.isNullOrEmpty(textView.getText().toString())) {
					
					RadioGroup radioGroup = (RadioGroup)
							findViewById(R.id.mainConfigurationWidgetURLsRadioGroup);
					
					int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
					if (checkedRadioButtonId != -1) {
						RadioButton childAt = (RadioButton) radioGroup.getChildAt(checkedRadioButtonId);
						String titleURL  = textView.getText().toString();
						saveWidget(titleURL, childAt.getText().toString());
						finish();
						
					} else {
						Toast.makeText(v.getContext(),
								getString(R.string.app_widget_message_add_missing_url),
								Toast.LENGTH_LONG).show();
					}
				}					
			}
		});
		
		View cancelButton = findViewById(R.id.mainConfigurationWidgetCancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void saveWidget(String titleURL, String URL) {
		PreferencesUtils.setWidgetPreferences(this, mAppWidgetId, titleURL, URL);
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(NavBroWidgetConfigurationActivity.this);
        NavBroWidgetActivity.updateAppWidget(NavBroWidgetConfigurationActivity.this,
        		appWidgetManager, mAppWidgetId, titleURL, URL);
        
		Intent intent = new Intent();
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, intent);
	}
}
