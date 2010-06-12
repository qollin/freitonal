package de.cr.freitonal.usertests.client.rpc;

import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.AMajor;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Eroica;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Ordinal4a;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Piano;
import static de.cr.freitonal.usertests.client.test.data.FullSearchInformation.Violin;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.RequestBuilderFactory;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.gwt.DTOParserGWT;
import de.cr.freitonal.usertests.client.test.data.TestResources;

public class RPCServiceImplTest extends GWTTestCase {

	private RPCServiceImpl rpcService;
	private PieceSearchMask pieceSearchMask;
	private TestResources resources;

	@Override
	public void gwtSetUp() {
		rpcService = new RPCServiceImpl(new DTOParserGWT());
		resources = GWT.create(TestResources.class);
		ModelFactory modelFactory = new ModelFactory(new DTOParserGWT());
		SearchResult searchResult = modelFactory.createSearchResult(resources.getFullSearchJSON().getText());

		pieceSearchMask = searchResult.getPieceSearchMask();
	}

	private void assertNextSearchStringMatches(final String regex) {
		rpcService.setRequestBuilderFactory(new RequestBuilderFactory() {
			@Override
			public RequestBuilder createRequestBuilder(Method httpMethod, String url) {
				assertTrue("URL does not match regex\nURL = " + url + "\nRegex = " + regex, url.matches(regex));
				return new RequestBuilder(httpMethod, url) {
					@Override
					public com.google.gwt.http.client.Request sendRequest(String requestData, RequestCallback callback) {
						return null;
					}
				};
			}
		});
	}

	@Test
	public void testComposerSearch() {
		pieceSearchMask.getComposers().setSelected(Beethoven);
		assertNextSearchStringMatches("^[^?]+\\?piece-composer=" + Beethoven.id + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testComposerAndInstrumentSearch() {
		pieceSearchMask.getComposers().setSelected(Beethoven);
		pieceSearchMask.getInstrumentations().setSelectedList(Piano, Violin);
		assertNextSearchStringMatches("^[^?]+\\?piece-composer=" + Beethoven.id + "\\&piece-instrumentations__instrument=" + Piano.id
				+ "\\&piece-instrumentations__instrument=" + Violin.id + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testSubtitleSearch() {
		pieceSearchMask.getSubtitles().setSelected(Eroica);
		assertNextSearchStringMatches("^[^?]+\\?piece-subtitle=" + Eroica.id + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testOrdinalSearch() {
		pieceSearchMask.getOrdinals().setSelected(Ordinal4a);
		assertNextSearchStringMatches("^[^?]+\\?piece-type_ordinal=" + Ordinal4a.id + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testMusicKeySearch() {
		pieceSearchMask.getMusicKeys().setSelected(AMajor);
		assertNextSearchStringMatches("^[^?]+\\?piece-music_key=" + AMajor.id + "$");
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
