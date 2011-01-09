package de.cr.freitonal.usertests.gui;

import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class CreatePieceTest extends UserTestCase {
	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	private void verifySearchResult() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				assertEquals(1, ((SearchResult) parameters[0]).getPieceSearchMask().getComposers().size());
				finishTest();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void testInitialLoading() {
		Future<Item> mozart = createComposer("Mozart");
		Future<Item> piano = createInstrument("Piano");
		createInstrumentation("solo-piano", piano);
		Future<Item> opus = createCatalogName("Opus");
		//Future<Catalog> opus27_1 = createCatalog(opus, "27-1");
		Future<Item> sonata = createPieceType("Sonata");

		runApplication();
		clickAddPieceButton();

		selectComposer(mozart);
		selectInstrument(piano);
		selectCatalogName(opus);
		enterCatalogNumber("27-1");
		selectPieceType(sonata);

		clickSavePieceButton();

		//verifySearchResult();

		go();
	}
}
