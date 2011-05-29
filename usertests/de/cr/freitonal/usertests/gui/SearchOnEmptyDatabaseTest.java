package de.cr.freitonal.usertests.gui;

import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class SearchOnEmptyDatabaseTest extends UserTestCase {
	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	private void verifySearchResult() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				assertEquals(0, ((SearchResult) parameters[0]).getPieceSearchMask().getComposers().size());
				runNextScript();
			}
		});
	}

	public void testInitialLoading() {
		runApplication();
		verifySearchResult();
		go();
	}
}
