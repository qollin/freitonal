package de.cr.freitonal.usertests.gui;

import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class PieceListTest extends UserTestCase {
	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	private void verifySearchResult() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				SearchResult searchResult = (SearchResult) parameters[0];
				assertEquals(1, searchResult.getPieceList().size());
				runNextScript();
			}
		});
	}

	public void testInitialLoading() {
		createTestObjects();
		createPiece(mozart, soloPiano, opus52, sonata);
		runApplication();
		verifySearchResult();
		go();
	}
}
