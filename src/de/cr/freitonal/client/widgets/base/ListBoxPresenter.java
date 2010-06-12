package de.cr.freitonal.client.widgets.base;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.SearchContext.IntialLoading;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSet;

public class ListBoxPresenter {
	public enum EventHandlingStrategy {
		FireGlobalSearchEventOnChange, RelayToRegisteredChangeEventHandlers
	}

	private View view;
	private final HandlerManager eventBus;
	private ItemSet items;
	private final EventHandlingStrategy strategy;
	private final ArrayList<ChangeHandler> changeHandlers = new ArrayList<ChangeHandler>();
	private ItemSet completeItemSet;
	private DisplayMode mode = DisplayMode.Select;

	public interface View extends HasChangeHandlers, HasClickHandlers {
		Item getSelectedItem();

		void setItems(ArrayList<Item> items);

		public int getItemCount();

		void setSelectedItem(Item selected);

		boolean isEnabled();

		void setEnabled(boolean enabled);

		void setDisplayMode(DisplayMode mode);

		void removeFromParent();
	}

	public ListBoxPresenter(HandlerManager eventBus, View view) {
		this(eventBus, view, EventHandlingStrategy.FireGlobalSearchEventOnChange);
	}

	public ListBoxPresenter(HandlerManager eventBus, View view, EventHandlingStrategy strategy) {
		this.eventBus = eventBus;
		this.view = view;
		this.strategy = strategy;
		bind();
	}

	private void bind() {
		view.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				fireOnNewItemSelected(view.getSelectedItem());
			}
		});

		view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireHandleClickEventOnCloseImage();
			}
		});
	}

	/**
	 * This method must only be called from a test
	 * 
	 * @param selectedItem
	 */
	public void fireOnNewItemSelected_TEST(Item selectedItem) {
		view.setSelectedItem(selectedItem);
		fireOnNewItemSelected(selectedItem);
	}

	private void fireOnNewItemSelected(Item selectedItem) {
		items.setSelected(selectedItem);
		view.setDisplayMode(DisplayMode.View);

		if (mode != Create) {
			fireSearchFieldChangedEvent();
		}
	}

	/**
	 * This method must only be called from a test
	 */
	public void fireHandleClickEventOnCloseImage_TEST() {
		fireHandleClickEventOnCloseImage();
	}

	private void fireHandleClickEventOnCloseImage() {
		view.setDisplayMode(mode);

		if (mode != Create) {
			fireSearchFieldChangedEvent();
		}
	}

	private void fireSearchFieldChangedEvent() {
		switch (strategy) {
		case FireGlobalSearchEventOnChange:
			eventBus.fireEvent(new SearchFieldChangedEvent());
			break;
		case RelayToRegisteredChangeEventHandlers:
			for (ChangeHandler changeHandler : changeHandlers) {
				changeHandler.onChange(null);
			}
			break;
		}
	}

	public int getItemCount() {
		return view.getItemCount();
	}

	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @return the selected Item or null if there is no selection
	 */
	public Item getSelectedItem() {
		return view.getSelectedItem();
	}

	public void setItems(ItemSet items, SearchContext searchContext) {
		this.items = items;
		view.setItems(items.getItems());
		if (items.getSelected() != null) {
			view.setSelectedItem(items.getSelected());
		}

		if (searchContext == IntialLoading) {
			this.completeItemSet = items;
		}
	}

	public void addChangeHandler(ChangeHandler changeHandler) {
		changeHandlers.add(changeHandler);
	}

	public boolean isEnabled() {
		return view.isEnabled();
	}

	public void setEnabled(boolean enabled) {
		view.setEnabled(enabled);
	}

	public void setDisplayMode(DisplayMode mode) {
		if (mode == null) {
			throw new IllegalArgumentException("mode must not be null");
		}

		this.mode = mode;
		if (mode == Create) {
			view.setItems(completeItemSet.getItems());
		}
		view.setDisplayMode(mode);
	}

	public void setItems(ItemSet itemSet) {
		setItems(itemSet, SearchContext.IntialLoading);
	}

	public ItemSet getFullItemSet() {
		return this.getFullItemSet();
	}

	public DisplayMode getDisplayMode() {
		return mode;
	}
}
