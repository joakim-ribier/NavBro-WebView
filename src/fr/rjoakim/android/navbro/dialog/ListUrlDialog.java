package fr.rjoakim.android.navbro.dialog;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fr.rjoakim.android.navbro.NavBroActivity;
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
public abstract class ListUrlDialog extends MyDialog<String> {

	private final Map<CheckBox, String> mapsIdToUrl;

	protected ListUrlDialog(NavBroActivity navAppActivity) {
		super(navAppActivity, R.layout.list_url_dialog);
		
		this.mapsIdToUrl = Maps.newHashMap();
		
		setTextOnPositiveButton(getString(R.string.list_url_title_dialog_delete));
		
		List<String> preferencesUrls = PreferencesUtils.listMyURL(navAppActivity);
		initUrlWidgets(preferencesUrls, (LinearLayout)view);
	}
	
	private void initUrlWidgets(List<String> myUrls, LinearLayout view) {
		for (final String url: myUrls) {
			
			View linearLayout = activity.getLayoutInflater(
					).inflate(R.layout.url_widget, null);
			
			
			View urlAccessButton = linearLayout.findViewById(R.id.urlWidgetViewLinearLayoutButton);
			urlAccessButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					onSuccess(url);
				}
			});
			
			TextView urlTextView = (TextView) linearLayout.findViewById(R.id.urlWidgetViewTextView);
			urlTextView.setText(url);
			
			CheckBox checkBox = (CheckBox)
					linearLayout.findViewById(R.id.urlWidgetViewCheckBox);

			view.addView(linearLayout);
			
			mapsIdToUrl.put(checkBox, url);
		}
	}

	@Override
	protected String getTitleView() {
		return getString(R.string.list_url_title_dialog);
	}
	
	@Override
	public void onFailed() {}
	
	@Override
	protected void onPositiveButton(View v) {
		dialog.dismiss();
		List<String> listMyUrl = Lists.newArrayList();
		for (Entry<CheckBox, String> entry: mapsIdToUrl.entrySet()) {
			if (!entry.getKey().isChecked()) {
				listMyUrl.add(entry.getValue());
			}
		}
		PreferencesUtils.setMyNewUrls((NavBroActivity) activity, listMyUrl);
	}
	
	protected void onNegativeButton(View v) {
		dialog.dismiss();
	}
}
