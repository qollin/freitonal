package de.cr.freitonal.client.rpc;

import com.google.gwt.http.client.RequestBuilder;

public class RequestBuilderFactory {
	public RequestBuilder createRequestBuilder(RequestBuilder.Method httpMethod, String url) {
		return new RequestBuilder(httpMethod, url);
	}
}
