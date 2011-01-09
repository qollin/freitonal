package de.cr.freitonal.usertests.api;

import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class CreateComposerTest extends APITestCase {
	private void verifyCreateComposerResult() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				assertEquals("Mozart", ((Item) parameters[0]).getValue());
				finishTest();
			}
		});
	}

	public void testCreatingAComposer() {
		createComposer(new VolatileItem("Mozart"));
		verifyCreateComposerResult();
		go();
	}

}
