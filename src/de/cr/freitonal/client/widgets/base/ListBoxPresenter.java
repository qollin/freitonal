package de.cr.freitonal.client.widgets.base;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSet;

public class ListBoxPresenter {
	private View view;
	private final HandlerManager eventBus;
	private ItemSet items;

	public interface View extends HasChangeHandlers {
		Item getSelectedItem();

		void setItems(ArrayList<Item> items);

		public int getItemCount();

		void setSelectedItem(Item selected);

	}

	public ListBoxPresenter(HandlerManager eventBus, View view) {
		this.eventBus = eventBus;
		this.view = view;
		bind();
	}

	private void bind() {
		view.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				onChangeEvent(event);
			}
		});
	}

	public void onChangeEvent(ChangeEvent event) {
		fireOnNewItemSelected(view.getSelectedItem());
	}

	public void fireOnNewItemSelected(Item selectedItem) {
		items.setSelected(selectedItem);
		eventBus.fireEvent(new SearchFieldChangedEvent(selectedItem));
	}

	public void setItems(ItemSet items) {
		this.items = items;
		view.setItems(items.getItems());
		if (items.getSelected() != null) {
			view.setSelectedItem(items.getSelected());
		}
	}

	public int getItemCount() {
		return view.getItemCount();
	}

	public void setView(View view) {
		this.view = view;
	}

	public Item getSelectedItem() {
		return view.getSelectedItem();
	}

	public void setItems(Item item) {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(item);
		setItems(new ItemSet(items));
	}
}
