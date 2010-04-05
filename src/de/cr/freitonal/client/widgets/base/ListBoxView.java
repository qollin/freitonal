package de.cr.freitonal.client.widgets.base;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;

import de.cr.freitonal.client.models.Item;

public class ListBoxView extends Composite implements ListBoxPresenter.View {
	interface Binder extends UiBinder<HTMLPanel, ListBoxView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	ListBox list;

	private final ArrayList<Item> specialItems = new ArrayList<Item>();

	@UiConstructor
	public ListBoxView() {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
	}

	public void setName(String name) {
		specialItems.add(new Item("-1", name));
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return list.addChangeHandler(handler);
	}

	private void addItems(ArrayList<Item> items) {
		for (Item item : items) {
			list.addItem(item.value, item.id);
		}
	}

	public void setItems(ArrayList<Item> items) {
		list.clear();
		addItems(specialItems);
		addItems(items);
	}

	public Item getSelectedItem() {
		int index = list.getSelectedIndex();
		if (index == -1) {
			return null;
		}

		if ("-1".equals(list.getValue(index))) {
			return null;
		}

		return new Item(list.getValue(index), list.getItemText(index));
	}

	public int getItemCount() {
		return list.getItemCount() - specialItems.size();
	}

	public void setSelectedItem(Item selected) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(selected.id)) {
				list.setSelectedIndex(i);
				break;
			}
		}
	}
}
