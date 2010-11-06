package de.cr.freitonal.unittests.client.widgets.base.listbox;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public abstract class ListBoxPresenterTest extends PresenterTest {
	protected Item firstItem = new Item("a", "a");
	protected Item secondItem = new Item("b", "b");
	protected ItemSet twoElementItemSet = new ItemSet(firstItem, secondItem);
	protected ItemSet oneElementItemSet = new ItemSet(firstItem);

	protected ListBoxViewMock view = new ListBoxViewMock(trace);
}
