package de.cr.freitonal.client.widgets.base;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.Item;

public class ListBoxView extends Composite implements ListBoxPresenter.View {
	interface Binder extends UiBinder<HTMLPanel, ListBoxView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	ListBox list;

	@UiField
	Label label;

	@UiField
	Image closeImage;

	private final ArrayList<Item> specialItems = new ArrayList<Item>();

	private DisplayMode mode;

	@UiConstructor
	public ListBoxView() {
		HTMLPanel panel = binder.createAndBindUi(this);
		initWidget(panel);
		switchToSelectMode();
	}

	public void setName(String name) {
		specialItems.add(new Item("-1", name));
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return list.addChangeHandler(handler);
	}

	/**
	 * Adds a click handler to the closeImage in the "view" DisplayMode
	 */
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return closeImage.addClickHandler(handler);
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

	public boolean isEnabled() {
		return list.isEnabled();
	}

	public int getItemCount() {
		if (mode == DisplayMode.View) {
			return 1;
		}

		return list.getItemCount() - specialItems.size();
	}

	public void setSelectedItem(Item selected) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getValue(i).equals(selected.id)) {
				list.setSelectedIndex(i);
				return;
			}
		}

		throw new IllegalArgumentException("the item " + selected + " is unknown for ");
	}

	public void setEnabled(boolean enabled) {
		list.setEnabled(enabled);
	}

	public void setDisplayMode(DisplayMode mode) {
		if (this.mode == mode) {
			return;
		}

		this.mode = mode;
		switch (mode) {
		case View:
			if (getSelectedItem() == null) {
				throw new IllegalArgumentException("you may not switch to view mode without a selected item");
			}
			switchToViewMode();
			break;
		case Select:
			switchToSelectMode();
			break;
		case Create:
			switchToCreateMode();
			break;
		default:
			throw new IllegalArgumentException("mode " + mode + " is not implemented yet");
		}
	}

	private void switchToSelectMode() {
		label.setVisible(false);
		closeImage.setVisible(false);

		list.setVisible(true);
	}

	private void switchToCreateMode() {
		switchToSelectMode();
		list.setSelectedIndex(0);
	}

	private void switchToViewMode() {
		label.setText(getSelectedItem().value);
		list.setVisible(false);

		label.setVisible(true);
		closeImage.setVisible(true);
	}
}
