package de.cr.freitonal.unittests.client.rpc;

import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.AMajor;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Beethoven;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Eroica;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Ordinal4a;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Piano;
import static de.cr.freitonal.unittests.client.test.data.FullSearchInformation.Violin;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;

import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.RequestBuilderFactory;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.java.DTOParserJava;
import de.cr.freitonal.unittests.client.test.data.TestResources;

public class RPCServiceImplTest {

	private RPCServiceImpl rpcService;
	private PieceSearchMask pieceSearchMask;
	private TestResources resources;

	@Before
	public void setUp() {
		rpcService = new RPCServiceImpl(new DTOParserJava(), new URLEncoderMock());
		resources = new TestResources();
		ModelFactory modelFactory = new ModelFactory(new DTOParserJava());
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
		assertNextSearchStringMatches("^[^?]+\\?piece-composer=" + Beethoven.getID() + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testComposerAndInstrumentSearch() {
		pieceSearchMask.getComposers().setSelected(Beethoven);
		pieceSearchMask.getInstrumentations().setSelectedList(Piano, Violin);
		assertNextSearchStringMatches("^[^?]+\\?piece-composer=" + Beethoven.getID() + "\\&piece-instrumentations__instrument=" + Piano.getID()
				+ "\\&piece-instrumentations__instrument=" + Violin.getID() + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testSubtitleSearch() {
		pieceSearchMask.getSubtitles().setSelected(Eroica);
		assertNextSearchStringMatches("^[^?]+\\?piece-subtitle=" + Eroica.getID() + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testOrdinalSearch() {
		pieceSearchMask.getOrdinals().setSelected(Ordinal4a);
		assertNextSearchStringMatches("^[^?]+\\?piece-type_ordinal=" + Ordinal4a.getID() + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testMusicKeySearch() {
		pieceSearchMask.getMusicKeys().setSelected(AMajor);
		assertNextSearchStringMatches("^[^?]+\\?piece-music_key=" + AMajor.getID() + "$");
		rpcService.search(pieceSearchMask, null);
	}

	@Test
	public void testSelectedItemsInSearchMaskStaySelectedAfterSearch() {
		pieceSearchMask.getComposers().setSelected(Beethoven);
		SearchResult searchResult = rpcService.createSearchResult(pieceSearchMask, resources.getSearchForBeethovenJSON().getText());
		assertEquals(Beethoven, searchResult.getPieceSearchMask().getComposers().getSelected());
	}
}
