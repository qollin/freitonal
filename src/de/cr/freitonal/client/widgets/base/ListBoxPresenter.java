package de.cr.freitonal.client.widgets.base;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.Select;
import static de.cr.freitonal.client.event.DisplayMode.View;

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
import de.cr.freitonal.client.event.SimpleDFA;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

public class ListBoxPresenter extends BasePresenter implements SelectablePresenter {
	private View view;
	protected ItemSet itemSet;
	private final ArrayList<ChangeHandler> changeHandlers = new ArrayList<ChangeHandler>();
	private ItemSet completeItemSet;
	protected final SimpleDFA dfa = new SimpleDFA();

	public interface View extends HasChangeHandlers, HasClickHandlers, de.cr.freitonal.client.widgets.base.View {
		Item getSelectedItem();

		void setItems(ArrayList<Item> items);

		void setSelectedItem(Item selected);

		boolean isEnabled();

		void setEnabled(boolean enabled);

		void setDisplayMode(DisplayMode mode);
	}

	public ListBoxPresenter(HandlerManager eventBus, View view) {
		super(eventBus);
		this.view = view;
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

	protected void initializeDFA() {
		dfa.addTransition("Init", "setItems", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				ItemSet itemSet = (ItemSet) parameters[0];

				ListBoxPresenter.this.itemSet = itemSet;
				view.setItems(itemSet.getItems());

				ListBoxPresenter.this.completeItemSet = itemSet;
			}
		});
		dfa.addTransition("Select", "setItems", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				ItemSet itemSet = (ItemSet) parameters[0];

				ListBoxPresenter.this.itemSet = itemSet;
				view.setItems(itemSet.getItems());

				if (itemSet.getSelected() != null) {
					view.setSelectedItem(itemSet.getSelected());
				}
			}
		});

		dfa.addTransition("Select", "fireOnNewItemSelected", "View", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				Item selectedItem = (Item) parameters[0];
				itemSet.setSelected(selectedItem);

				view.setDisplayMode(View);
				fireListBoxChangedEvent();
			}
		});
		dfa.addTransitionWithTriggerParam("Select", "setDisplayMode", Create, "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				itemSet = completeItemSet;
				view.setItems(completeItemSet.getItems());
				view.setDisplayMode(Create);
			}
		});
		dfa.addTransition("View", "fireHandleClickEventOnExitViewMode", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				view.setDisplayMode(Select);
				fireListBoxChangedEvent();
			}
		});
		dfa.addTransitionWithTriggerParam("View", "setDisplayMode", Create, "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				itemSet = completeItemSet;
				view.setItems(completeItemSet.getItems());
			}
		});
		dfa.addTransition("View", "setItems", "View");
		dfa.addTransition("Create", "fireOnNewItemSelected", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				Item selectedItem = (Item) parameters[0];
				itemSet.setSelected(selectedItem);
				fireListBoxChangedEvent();
			}
		});
		dfa.addTransition("Create", "fireHandleClickEventOnExitViewMode", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				view.setDisplayMode(Create);
			}
		});

		dfa.start("Init");
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

	protected void fireListBoxChangedEvent() {
		for (ChangeHandler changeHandler : changeHandlers) {
			changeHandler.onChange(null);
		}
	}

	public int getItemCount() {
		if (dfa.getState().equals("View")) {
			return 1;
		}

		return itemSet.size();
	}

	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @return the selected Item or null if there is no selection
	 */
	public Item getSelectedItem() {
		return itemSet.getSelected();
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
		dfa.transition("setItems", itemSet);
	}

	public ItemSet getFullItemSet() {
		return completeItemSet;
	}

	public Item getItem(int index) {
		return itemSet.getItem(index);
	}

	public View getView() {
		return view;
	}

}
