package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.models.factories.SubtitleSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class ASubtitleSetFactoryShould extends FactoryTest {

	@Test
	public void ReturnACorrectSubtitleSet() {
		DTOObject jsonObject = parse("{'piece-subtitle': [['Eroica', 'Eroica']]}");

		SubtitleSetFactory factory = new SubtitleSetFactory();
		SubtitleSet subtitleSet = factory.createSubtitleSet(jsonObject);

		assertEquals(new Item("Eroica", "Eroica"), subtitleSet.getItem(0));
	}
}
