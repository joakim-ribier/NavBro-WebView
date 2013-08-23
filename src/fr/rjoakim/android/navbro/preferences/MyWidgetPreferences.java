package fr.rjoakim.android.navbro.preferences;

import com.google.common.base.Objects;

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
public class MyWidgetPreferences {

	private final String titleURL;
	private final String URL;
	
	public MyWidgetPreferences(String titleURL, String uRL) {
		this.titleURL = titleURL;
		this.URL = uRL;
	}

	public String getTitleURL() {
		return titleURL;
	}

	public String getURL() {
		return URL;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(titleURL, URL);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof MyWidgetPreferences) {
			MyWidgetPreferences that = (MyWidgetPreferences) object;
			return Objects.equal(this.titleURL, that.titleURL)
				&& Objects.equal(this.URL, that.URL);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("titleURL", titleURL)
			.add("URL", URL)
			.toString();
	}
}
