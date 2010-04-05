package de.cr.freitonal.client.rpc;

import static de.cr.freitonal.client.test.data.FullSearchInformation.Beethoven;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.test.data.TestResources;

public class RPCServiceImplTest extends GWTTestCase {

	private RPCServiceImpl rpcService;
	private PieceSearchMask pieceSearchMask;
	private TestResources resources;

	@Override
	public void gwtSetUp() {
		rpcService = new RPCServiceImpl();
		resources = GWT.create(TestResources.class);
		ModelFactory modelFactory = new ModelFactory();
		SearchResult searchResult = modelFactory.createSearchResult(resources.getFullSearchJSON().getText());

		pieceSearchMask = searchResult.getPieceSearchMask();
	}

	@Test
	public void testComposerSearch() {
		pieceSearchMask.getComposers().setSelected(Beethoven);
		rpcService.setRequestBuilderFactory(new RequestBuilderFactory() {
			@Override
			public RequestBuilder createRequestBuilder(Method httpMethod, String url) {
				assertTrue("URL should include the searched for composer", url.matches("^[^?]+\\?piece-composer=" + Beethoven.id + "$"));
				return new RequestBuilder(httpMethod, url) {
					@Override
					public com.google.gwt.http.client.Request sendRequest(String requestData, RequestCallback callback) {
						return null;
					}
				};
			}
		});

		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testSelectedItemsInSearchMaskStaySelectedAfterSearch() {
		pieceSearchMask.getComposers().setSelected(Beethoven);
		SearchResult searchResult = rpcService.createSearchResult(pieceSearchMask, resources.getSearchForBeethovenJSON().getText());
		assertEquals(Beethoven, searchResult.getPieceSearchMask().getComposers().getSelected());
	}

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

}
