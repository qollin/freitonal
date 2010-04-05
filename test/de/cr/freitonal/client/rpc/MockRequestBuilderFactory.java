package de.cr.freitonal.client.rpc;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.RequestBuilder.Method;

public class MockRequestBuilderFactory extends RequestBuilderFactory {
	private final Callback callback;

	public interface Callback {
		public void onSendRequest(String requestData, RequestCallback requestCallback);
	}

	public MockRequestBuilderFactory(Callback callback) {
		this.callback = callback;
	}

	@Override
	public RequestBuilder createRequestBuilder(Method httpMethod, String url) {
		return new RequestBuilder(httpMethod, url) {
			@Override
			public Request sendRequest(String requestData, RequestCallback requestCallback) throws RequestException {
				callback.onSendRequest(requestData, requestCallback);
				return null;
			}
		};
	}
}
