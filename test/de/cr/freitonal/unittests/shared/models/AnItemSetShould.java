package de.cr.freitonal.unittests.shared.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

public class AnItemSetShould {
	private ItemSet set;

	@Before
	public void setupItemSet() {
		set = new ItemSet(new Item("1", "1"), new Item("a", "b"));
	}

	@Test
	public void PrintNicelyWithToString() {
		assertEquals("[1: 1, a: b]", set.toString());
	}
}
