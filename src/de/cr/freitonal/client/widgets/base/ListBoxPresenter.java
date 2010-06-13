package de.cr.freitonal.client.widgets.base;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.Select;
import static de.cr.freitonal.client.event.DisplayMode.View;
import static de.cr.freitonal.client.event.SearchContext.IntialLoading;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.AbstractTransitionAction;
import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SimpleDFA;
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
	private final SimpleDFA dfa = new SimpleDFA();

	public interface View extends HasChangeHandlers, HasClickHandlers {
		Item getSelectedItem();

		void setItems(ArrayList<Item> items);

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
		initializeDFA();
	}

	private void bind() {
		view.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				fireOnNewItemSelected(view.getSelectedItem());
			}
		});

		view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireHandleClickEventOnExitViewMode();
			}
		});
	}

	private void initializeDFA() {
		dfa.addTransition("Select", "fireOnNewItemSelected", "View", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				Item selectedItem = (Item) parameters[0];
				items.setSelected(selectedItem);

				view.setDisplayMode(View);
				fireSearchFieldChangedEvent();
			}
		});
		dfa.addTransition("Select", "setDisplayMode", Create, "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				items = completeItemSet;
				view.setItems(completeItemSet.getItems());
			}
		});
		dfa.addTransition("View", "fireHandleClickEventOnExitViewMode", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				view.setDisplayMode(Select);
				fireSearchFieldChangedEvent();
			}
		});
		dfa.addTransition("View", "setDisplayMode", Create, "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				items = completeItemSet;
				view.setItems(completeItemSet.getItems());
			}
		});
		dfa.addTransition("Create", "fireOnNewItemSelected", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				Item selectedItem = (Item) parameters[0];
				items.setSelected(selectedItem);
			}
		});
		dfa.addTransition("Create", "fireHandleClickEventOnExitViewMode", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				view.setDisplayMode(Create);
			}
		});

		dfa.start("Select");
	}

	public void fireOnNewItemSelected(Item selectedItem) {
		dfa.transition("fireOnNewItemSelected", selectedItem);
	}

	/**
	 * This method must only be called from a test
	 */
	public void fireHandleClickEventOnCloseImage_TEST() {
		fireHandleClickEventOnExitViewMode();
	}

	private void fireHandleClickEventOnExitViewMode() {
		dfa.transition("fireHandleClickEventOnExitViewMode");
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
		if (dfa.getState().equals("View")) {
			return 1;
		}

		return items.size();
	}

	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @return the selected Item or null if there is no selection
	 */
	public Item getSelectedItem() {
		return items.getSelected();
		//return view.getSelectedItem();
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
		dfa.transitionWithTriggerParam("setDisplayMode", mode);
	}

	public void setItems(ItemSet itemSet) {
		setItems(itemSet, SearchContext.IntialLoading);
	}

	public ItemSet getFullItemSet() {
		return this.getFullItemSet();
	}
}
