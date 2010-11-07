package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.factories.ComposerSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class AComposerSetFactoryShould extends FactoryTest {
	private ComposerSetFactory composerSetFactory;

	@Before
	public void setupComposerSetFactory() {
		composerSetFactory = new ComposerSetFactory();
	}

	@Test
	public void ReturnACorrectComposerSet() {
		DTOObject jsonObject = parse("{'piece-composer': [['1', 'Beethoven'], ['2', 'Mozart']]}");
		ComposerSet composerSet = composerSetFactory.createComposerSet(jsonObject);

		assertEquals(2, composerSet.size());
		assertEquals(new Item("1", "Beethoven"), composerSet.getItem(0));
		assertEquals(new Item("2", "Mozart"), composerSet.getItem(1));
	}

	@Test
	public void ThrowAnExceptionIfTheRequiredDataIsInTheWrongFormat() {
		DTOObject jsonObject = parse("{'piece-composer': {}}");
		try {
			composerSetFactory.createComposerSet(jsonObject);
			fail();
		} catch (IllegalArgumentException e) {

		}
	}
}
