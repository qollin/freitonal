package de.cr.freitonal.unittests.client.rpc;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.URLEncoder;
import de.cr.freitonal.client.rpc.java.DTOParserJava;
import de.cr.freitonal.unittests.client.test.data.TestResources;

public abstract class JSONTestCase {
	protected AppController appController;
	protected TestResources resources;

	protected final DTOParserJava parser = new DTOParserJava();
	protected final URLEncoder urlEncoder = new URLEncoderMock();

	protected final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void setUp() {
		resources = new TestResources();
		trace.clear();
	}

	protected void onNextSearchReturn(final String jsonString) {
		appController.setRPCService(new RPCServiceImpl(parser, urlEncoder) {
			@Override
			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
				trace.add("search");
				callback.onSuccess(createSearchResult(searchMask, jsonString));
			}
		});
	}

	protected void onNextSearchFail() {
		appController.setRPCService(new RPCServiceImpl(parser, urlEncoder) {
			@Override
			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
				fail("the search method should not have been called");
			}
		});
	}
}
