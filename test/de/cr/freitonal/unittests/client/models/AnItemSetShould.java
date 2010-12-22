package de.cr.freitonal.unittests.client.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

public class AnItemSetShould {
	private ItemSet itemSet;
	private final Item item1 = new Item("1", "1");
	private final Item item2 = new Item("2", "2");

	@Before
	public void setupItemSet() {
		itemSet = new ItemSet(item1, item2);
	}

	@Test
	public void ReturnTheCorrectItem() {
		assertEquals(item1, itemSet.getItem(0));
		assertEquals(item2, itemSet.getItem(1));
	}

	@Test
	public void AllowRemovingTheSelection() {
		itemSet.setSelected(item1);
		itemSet.removeSelection();
		assertNull(itemSet.getSelected());
	}
}
