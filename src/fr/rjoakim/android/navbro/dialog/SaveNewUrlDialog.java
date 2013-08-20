package fr.rjoakim.android.navbro.dialog;

import android.app.Activity;
import android.view.View;
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
public abstract class SaveNewUrlDialog extends MyDialog<String> {

	protected SaveNewUrlDialog(Activity activity) {
		super(activity, R.layout.save_new_url_dialog);
	}
	
	@Override
	protected String getTitleView() {
		return getString(R.string.save_new_url_title_dialog);
	}
	
	@Override
	public void onFailed() {}
	
	@Override
	protected void onPositiveButton(View v) {
		dialog.dismiss();
		onSuccess(
				getValueFromEditText(R.id.saveNewUrlDialogText));
	}
	
	protected void onNegativeButton(View v) {
		dialog.dismiss();
	};
}
