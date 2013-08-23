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
public class MyWebViewPreferences {

	private final boolean javaScriptEnabled;
	private final boolean builtInZoomControls;
	private final boolean useWideViewPort;

	public MyWebViewPreferences(boolean javaScriptEnabled,
			boolean builtInZoomControls, boolean useWideViewPort) {
		
		this.javaScriptEnabled = javaScriptEnabled;
		this.builtInZoomControls = builtInZoomControls;
		this.useWideViewPort = useWideViewPort;
	}

	public boolean isJavaScriptEnabled() {
		return javaScriptEnabled;
	}
	
	public boolean isBuiltInZoomControls() {
		return builtInZoomControls;
	}

	public boolean isUseWideViewPort() {
		return useWideViewPort;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(javaScriptEnabled, builtInZoomControls, useWideViewPort);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof MyWebViewPreferences) {
			MyWebViewPreferences that = (MyWebViewPreferences) object;
			return Objects.equal(this.javaScriptEnabled, that.javaScriptEnabled)
				&& Objects.equal(this.builtInZoomControls, that.builtInZoomControls)
				&& Objects.equal(this.useWideViewPort, that.useWideViewPort);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("javaScriptEnabled", javaScriptEnabled)
			.add("builtInZoomControls", builtInZoomControls)
			.add("useWideViewPort", useWideViewPort)
			.toString();
	}
}
