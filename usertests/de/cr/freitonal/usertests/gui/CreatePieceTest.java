package de.cr.freitonal.usertests.gui;

import static de.cr.freitonal.client.event.DisplayMode.View;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class CreatePieceTest extends UserTestCase {
	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	private void verifyResult() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				assertEquals("The catalog presenter should be in view mode, because the just created Piece should be shown", View, appController
						.getPiecePresenter().getCatalogPresenter().getDisplayMode());
				runNextScript();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void testCreatingASimplePiece() {
		Future<Item> mozart = createComposer("Mozart");
		Future<Item> piano = createInstrument("Piano");
		createInstrumentation("solo-piano", piano);
		Future<Item> opus = createCatalogName("Opus");
		Future<Item> sonata = createPieceType("Sonata");

		runApplication();
		clickAddPieceButton();

		selectComposer(mozart);
		selectInstrument(piano);
		selectCatalogName(opus);
		enterCatalogNumber("27-1");
		selectPieceType(sonata);

		clickSavePieceButton();

		verifyResult();

		go();
	}
}
