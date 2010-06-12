package de.cr.freitonal.usertests.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.gwt.DTOParserGWT;
import de.cr.freitonal.usertests.client.test.data.TestResources;

public abstract class JSONTestCase extends GWTTestCase {
	protected AppController appController;
	protected TestResources resources;

	@Override
	public void gwtSetUp() {
		resources = GWT.create(TestResources.class);
	}

	protected void onNextSearchReturn(final String jsonString) {
		appController.setRPCService(new RPCServiceImpl(new DTOParserGWT()) {
			@Override
			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
				callback.onSuccess(createSearchResult(searchMask, jsonString));
			}
		});
	}

	protected void onNextSearchFail() {
		appController.setRPCService(new RPCServiceImpl(new DTOParserGWT()) {
			@Override
			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
				fail("the search method should not have been called");
			}
		});
	}

}
