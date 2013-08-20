package fr.rjoakim.android.navbro.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import fr.rjoakim.android.navbro.MyMenu;
import fr.rjoakim.android.navbro.NavBroActivity;
import fr.rjoakim.android.navbro.R;

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
public class DisplayWebSourceAsyncTask extends AsyncTask<String, String, String> {

	private final ViewAnimator viewAnimator;
	private final ScrollView view;
	private final NavBroActivity navAppActivity;
	private final TableLayout tableLayout;

	public DisplayWebSourceAsyncTask(NavBroActivity navAppActivity,
			ViewAnimator viewAnimator, MyMenu myMenu) {
		
		this.navAppActivity = navAppActivity;
		this.viewAnimator = viewAnimator;
		
		this.view = (ScrollView) this.viewAnimator.getChildAt(NavBroActivity.WEB_SOURCE_INDEX);
		this.tableLayout = (TableLayout) view.findViewById(R.id.webSourceViewLinearLayout);
		
		myMenu.showContent(NavBroActivity.WEB_SOURCE_INDEX);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		ImageView image = (ImageView) view.findViewById(R.id.webSourceViewLoadingImg);
		image.setImageResource(R.drawable.loading);
		
		TextView text = (TextView) view.findViewById(R.id.webSourceViewLoadingText);
		text.setText(navAppActivity.getString(R.string.web_source_loading));
		
		tableLayout.setVisibility(View.GONE);
		tableLayout.removeAllViews();
		
		view.findViewById(R.id.webSourceViewLoading
				).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		View inflate = navAppActivity.getLayoutInflater().inflate(R.layout.web_source_widget, null);
		
		TextView lineNumberTextView = (TextView)
				inflate.findViewById(R.id.webSourceWidgetLineNumberTextView);
		
		TextView textView = (TextView)
				inflate.findViewById(R.id.webSourceWidgetLineTextView);
		
		lineNumberTextView.setText(values[0]);
		textView.setText(values[1]);
		
		tableLayout.addView(inflate);
	}
	
	@Override
	protected String doInBackground(String... params) {
		BufferedReader in = null;
		try {
			URL site = new URL(params[0]);
			in = new BufferedReader(
					new InputStreamReader(site.openStream()));
			String inputLine;
			int cpt = 1;
			while ((inputLine = in.readLine()) != null) {
				publishProgress(String.valueOf(cpt++), inputLine);
			}
			in.close();
		} catch (IOException ex) {
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				return null;
			}
		}
		return "success";
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result != null) {
			view.findViewById(R.id.webSourceViewLoading
					).setVisibility(View.GONE);
			tableLayout.setVisibility(View.VISIBLE);
		} else {
			ImageView image = (ImageView) view.findViewById(R.id.webSourceViewLoadingImg);
			image.setImageResource(R.drawable.crash);
			
			TextView text = (TextView) view.findViewById(R.id.webSourceViewLoadingText);
			text.setText(navAppActivity.getString(R.string.web_source_oops));
		}
	}
}
