package de.cr.freitonal.unittests.client.rpc;

import java.io.UnsupportedEncodingException;

import de.cr.freitonal.client.rpc.URLEncoder;

public class URLEncoderMock implements URLEncoder {

	public String encode(String absolutePath) {
		return encodeParam("http://localhost/classical" + absolutePath);
	}

	public String encodeParam(String key) {
		try {
			return java.net.URLEncoder.encode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
