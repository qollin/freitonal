package de.cr.freitonal.client.widgets.base.listbox;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.shared.models.Item;

public interface IListBoxView extends HasChangeHandlers, HasClickHandlers, de.cr.freitonal.client.widgets.base.View {
	Item getSelectedItem();

	void setItems(ArrayList<Item> items);

	void setSelectedItem(Item selected);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	boolean isVisible();

	void setDisplayMode(DisplayMode mode);
}