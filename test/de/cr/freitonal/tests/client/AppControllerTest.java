package de.cr.freitonal.tests.client;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;

public class AppControllerTest extends EasyMockSupport {
	private PiecePresenter.View pieceViewMock;
	private ComposerPresenter.View composerViewMock;
	private RPCService rpcServiceMock;

	@Before
	public void setUp() {
		pieceViewMock = createStrictMock(PiecePresenter.View.class);
		composerViewMock = createStrictMock(ComposerPresenter.View.class);

		rpcServiceMock = createStrictMock(RPCService.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testProcessSearchFieldChangedEvent() {
		expect(pieceViewMock.getComposerView()).andReturn(composerViewMock);
		expect(pieceViewMock.getCatalogView()).andReturn(null);
		expect(pieceViewMock.getPieceTypeView()).andReturn(null);
		expect(pieceViewMock.getInstrumentationView()).andReturn(null);

		rpcServiceMock.search((AsyncCallback<SearchResult>) anyObject());

		replay(pieceViewMock);
		replay(composerViewMock);
		replay(rpcServiceMock);

		AppController appController = new AppController(pieceViewMock, rpcServiceMock);
		appController.getPiecePresenter().getComposerPresenter().getListBoxPresenter().onChangeEvent(null);
		assertEquals("Search", appController.getDFA().getCurrentState().getName());

		verify(pieceViewMock);
		verify(composerViewMock);
		verify(rpcServiceMock);
	}
}
