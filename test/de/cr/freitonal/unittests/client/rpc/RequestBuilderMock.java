package de.cr.freitonal.unittests.client.rpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class RequestBuilderMock extends RequestBuilder {
	public RequestBuilderMock(Method httpMethod, String url) {
		super(httpMethod, url);
	}

	@Override
	public Request sendRequest(String requestData, RequestCallback callback) throws RequestException {
		return null;
	}
}
