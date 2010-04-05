package de.cr.freitonal.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RPCServiceImpl implements RPCService {
	private static final String JSON_URL = GWT.getHostPageBaseURL() + "classical";

	protected final ModelFactory modelFactory = new ModelFactory();
	protected final JSONFactory jsonFactory = new JSONFactory();

	private RequestBuilderFactory requestBuilderFactory = new RequestBuilderFactory();

	public void search(final PieceSearchMask searchMask, final AsyncCallback<SearchResult> callback) {
		String url = URL.encode(JSON_URL + "/search");
		if (searchMask != null) {
			url += jsonFactory.toHTTPParameters(searchMask);
		}

		RequestBuilder builder = requestBuilderFactory.createRequestBuilder(RequestBuilder.GET, url);

		try {
			builder.setHeader("Accept", "application/json");
			builder.setHeader("X-Requested-With", "XMLHttpRequest");

			builder.sendRequest(null, new RequestCallback() {
				public void onResponseReceived(Request request, Response response) {
					callback.onSuccess(createSearchResult(searchMask, response.getText()));
				}

				public void onError(Request request, Throwable exception) {
					System.out.println("got an error");
				}
			});
		} catch (RequestException e) {
			System.out.println("got an exception");
		}
	}

	public void search(AsyncCallback<SearchResult> callback) {
		search(null, callback);
	}

	public SearchResult createSearchResult(PieceSearchMask searchMask, String jsonString) {
		SearchResult result = modelFactory.createSearchResult(jsonString);

		if (searchMask != null) {
			searchMask.copyItemSelectionTo(result.getPieceSearchMask());
		}

		return result;
	}

	/**
	 * @param requestBuilderFactory
	 *            the requestBuilderFactory to set
	 */
	public void setRequestBuilderFactory(RequestBuilderFactory requestBuilderFactory) {
		this.requestBuilderFactory = requestBuilderFactory;
	}
}
