package de.cr.freitonal.tests.client.widgets.piece;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;

import java.util.ArrayList;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.client.widgets.composer.ComposerPresenter;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.client.widgets.piece.PiecePresenter;
import de.cr.freitonal.client.widgets.piecetype.PieceTypePresenter;
import de.cr.freitonal.tests.client.util.ArrayListEquals;

public class PieceTest extends EasyMockSupport {
	private PiecePresenter.View pieceViewMock;
	private RPCService rpcServiceMock;

	private PieceSearchMask pieceSearchMask;

	private static ArrayList<Item> eqArrayList(ArrayList<Item> in) {
		reportMatcher(new ArrayListEquals<Item>(in));
		return null;
	}

	@Before
	public void setUp() {
		rpcServiceMock = createStrictMock(RPCService.class);
		pieceViewMock = createStrictMock(PiecePresenter.View.class);
		pieceSearchMask = new PieceSearchMask();
	}

	@SuppressWarnings("unchecked")
	private void expectCallToSearchAndReturnPieceSearchMask(final PieceSearchMask mask) {
		rpcServiceMock.search((AsyncCallback<SearchResult>) anyObject());
		expectLastCall().andDelegateTo(new RPCService() {
			public void search(AsyncCallback<SearchResult> callback) {
				SearchResult result = new SearchResult();
				result.setPieceSearchMask(mask);
				callback.onSuccess(result);
			}

			public void search(PieceSearchMask searchMask, AsyncCallback<SearchResult> callback) {
			}
		});
	}

	@Test
	public void testProcessSearchResult() {
		ComposerPresenter.View composerViewMock = createStrictMock(ComposerPresenter.View.class);
		CatalogPresenter.View catalogViewMock = createStrictMock(CatalogPresenter.View.class);
		PieceTypePresenter.View pieceTypeViewMock = createStrictMock(PieceTypePresenter.View.class);
		InstrumentationPresenter.View instrumentationsViewMock = createStrictMock(InstrumentationPresenter.View.class);

		pieceSearchMask.setComposers(givenComposers());
		pieceSearchMask.setCatalogs(givenCatalogs());
		pieceSearchMask.setPieceTypes(givenPieceTypes());
		pieceSearchMask.setInstrumentations(givenInstrumentations());

		expectCallToSearchAndReturnPieceSearchMask(pieceSearchMask);
		expect(pieceViewMock.getComposerView()).andReturn(composerViewMock);
		expect(pieceViewMock.getCatalogView()).andReturn(catalogViewMock);
		expect(pieceViewMock.getPieceTypeView()).andReturn(pieceTypeViewMock);
		expect(pieceViewMock.getInstrumentationView()).andReturn(instrumentationsViewMock);

		/*
		expect(composerViewMock.getListBox()).andReturn(createNiceMock(HasChangeHandlers.class));
		composerViewMock.setItems(eqArrayList(expectedComposerItems()));
		catalogViewMock.setItems(eqArrayList(expectedCatalogNameItems()), eqArrayList(expectedCatalogNumberItems()));
		pieceTypeViewMock.setItems(eqArrayList(expectedPieceTypeItems()));
		instrumentationsViewMock.setInstrumentItems(eqArrayList(expectedInstrumentationItems()));
		*/
		replayAll();

		AppController appController = new AppController(pieceViewMock, rpcServiceMock);
		appController.go();

		verifyAll();
	}

	private InstrumentationSet givenInstrumentations() {
		return new InstrumentationSet(expectedInstrumentationItems());
	}

	private ArrayList<Item> expectedInstrumentationItems() {
		ArrayList<Item> instrumentations = new ArrayList<Item>();
		instrumentations.add(new Item("1", "Streichquartett"));
		return instrumentations;
	}

	private PieceTypeSet givenPieceTypes() {
		return new PieceTypeSet(expectedPieceTypeItems());
	}

	private ArrayList<Item> expectedPieceTypeItems() {
		ArrayList<Item> pieceTypes = new ArrayList<Item>();
		pieceTypes.add(new Item("1", "Sonate"));

		return pieceTypes;
	}

	private ComposerSet givenComposers() {
		return new ComposerSet(expectedComposerItems());
	}

	private ArrayList<Item> expectedComposerItems() {
		ArrayList<Item> composers = new ArrayList<Item>();
		composers.add(new Item("1", "Beethoven"));

		return composers;
	}

	private CatalogSet givenCatalogs() {
		return new CatalogSet(expectedCatalogNameItems(), expectedCatalogNumberItems());
	}

	private ArrayList<Item> expectedCatalogNameItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("1", "KV"));
		items.add(new Item("2", "Opus"));

		return items;
	}

	private ArrayList<Item> expectedCatalogNumberItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("1", "101"));
		items.add(new Item("2", "27-1"));
		items.add(new Item("3", "1-a"));

		return items;
	}
}
