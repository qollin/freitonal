package de.cr.freitonal.client.rpc;

import com.google.gwt.http.client.URL;

public class URLEncoderImpl implements URLEncoder {

	private final String hostPageBaseURL;

	public URLEncoderImpl(String hostPageBaseURL) {
		this.hostPageBaseURL = hostPageBaseURL + "classical";
	}

	public String encode(String absolutePath) {
		return URL.encode(hostPageBaseURL + absolutePath);
	}

	public String encodeParam(String key) {
		return URL.encode(key);
	}

}
