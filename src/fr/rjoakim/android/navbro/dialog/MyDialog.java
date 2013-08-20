package fr.rjoakim.android.navbro.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
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
public abstract class MyDialog<T> implements ResultView<T> {

	protected final Dialog dialog;
	protected final View view;
	protected final Activity activity;

	protected abstract void onPositiveButton(View v);
	protected abstract void onNegativeButton(View v);
	
	protected abstract String getTitleView();
	
	protected void initMyDialogLayout(View myDialogLayout) {}
	
	protected MyDialog(final Activity activity, final int resource) {
		this.activity = activity;
		this.view = activity.getLayoutInflater().inflate(resource, null);;
		
		View myDialogLayout = activity.getLayoutInflater(
				).inflate(R.layout.my_dialog, null);
		
		ScrollView scrollView = (ScrollView) 
				myDialogLayout.findViewById(R.id.myDialogMainView);
		scrollView.addView(view);
		
		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(myDialogLayout);
		dialog.setCancelable(false);
		
		setOnClickOnPositiveButton(myDialogLayout);
		setOnClickOnNegativeButton(myDialogLayout);
		
		TextView titleView = (TextView) myDialogLayout.
				findViewById(R.id.myDialogTitleTextView);
		titleView.setText(getTitleView());

		initMyDialogLayout(myDialogLayout);
	}
	
	private void setOnClickOnNegativeButton(View myDialogLayout) {
		View onNegativeButton = myDialogLayout.findViewById(R.id.myDialogNegativeButton);
		onNegativeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onNegativeButton(v);
			}
		});
	}
	
	private void setOnClickOnPositiveButton(View myDialogLayout) {
		Button onPositiveButton = (Button) myDialogLayout.findViewById(R.id.myDialogPositiveButton);
		onPositiveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onPositiveButton(v);
			}
		});
	}
	
	protected void setTextOnPositiveButton(String text) {
		((Button) dialog.findViewById(
				R.id.myDialogPositiveButton)).setText(text);
	}
	
	protected void setTextOnNegativeButton(String text) {
		((Button) dialog.findViewById(
				R.id.myDialogNegativeButton)).setText(text);
	}
	
	public void show() {
		dialog.show();
	}
	
	protected String getValueFromEditText(int id) {
		EditText editText = (EditText) view.findViewById(id);
		return editText.getText().toString();
	}

	public String getString(int resource) {
		return activity.getString(resource);
	}
	
	public String getString(int resource, Object... objects) {
		return activity.getString(resource, objects);
	}
}
