package de.cr.freitonal.unittests.client.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSetMultiSelection;

public class ItemSetMultiSelectionTest {

	private ArrayList<Item> items;
	private final Item item1 = new Item("1", "1");
	private final Item item2 = new Item("1", "2");

	@Before
	public void setUp() {
		items = new ArrayList<Item>();
		items.add(item1);
		items.add(item2);
	}

	@Test
	public void testConstructor() {
		ItemSetMultiSelection itemSet = new ItemSetMultiSelection(items);
		assertEquals("the item set should contain two items", 2, itemSet.getItems().size());
		assertEquals("the item set should not have a selection", 0, itemSet.getSelectedList().size());

		itemSet = new ItemSetMultiSelection(item1, item2);
		assertEquals("the item set should contain two items", 2, itemSet.getItems().size());
		assertEquals("the item set should not have a selection", 0, itemSet.getSelectedList().size());
	}

	@Test
	public void testSelection() {
		ItemSetMultiSelection itemSet = new ItemSetMultiSelection(items);

		itemSet.setSelectedList(items);
		assertEquals("the item set should have to items selected", 2, itemSet.getSelectedList().size());

		itemSet.setSelectedList(item1, item2);
		assertEquals("the item set should have to items selected", 2, itemSet.getSelectedList().size());
	}
}
