package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.factories.MusicKeySetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class AMusicKeySetFactoryShould extends FactoryTest {

	@Test
	public void ReturnACorrectMusicKeySet() {
		DTOObject jsonObject = parse("{'piece-music_key': [['1', 'C-Dur']]}");
		MusicKeySetFactory factory = new MusicKeySetFactory();
		MusicKeySet musicKeySet = factory.createMusicKeySet(jsonObject);

		assertEquals(new Item("1", "C-Dur"), musicKeySet.getItem(0));
	}
}
