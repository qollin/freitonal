package de.cr.freitonal.unittests.client.widgets.base.listbox;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

public abstract class ListBoxPresenterTest {
	protected Item firstItem = new Item("a", "a");
	protected Item secondItem = new Item("b", "b");
	protected ItemSet twoElementItemSet = new ItemSet(firstItem, secondItem);
	protected ItemSet oneElementItemSet = new ItemSet(firstItem);

	protected final ArrayList<String> trace = new ArrayList<String>();
	protected ListBoxViewMock view = new ListBoxViewMock(trace);
	protected HandlerManager eventBus = new HandlerManager(null);
}
