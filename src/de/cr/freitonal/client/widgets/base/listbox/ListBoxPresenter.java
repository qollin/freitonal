package de.cr.freitonal.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static de.cr.freitonal.client.event.DisplayMode.Select;
import static de.cr.freitonal.client.event.DisplayMode.View;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.EqualsTriggerParam;
import de.cr.freitonal.client.event.dfa.Transition;
import de.cr.freitonal.client.event.dfa.Trigger;
import de.cr.freitonal.client.event.dfa.TriggerParam;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.BasePresenter;
import de.cr.freitonal.client.widgets.base.SelectablePresenter;
import de.cr.freitonal.shared.models.Item;

public class ListBoxPresenter extends BasePresenter implements SelectablePresenter {
	private IListBoxView view;
	protected ItemSet itemSet;
	private final ArrayList<ChangeHandler> changeHandlers = new ArrayList<ChangeHandler>();
	private ItemSet completeItemSet;
	protected final DFA dfa = new DFA();
	private final ArrayList<HandlerRegistration> viewHandlers = new ArrayList<HandlerRegistration>();

	public ListBoxPresenter(EventBus eventBus, IListBoxView view) {
		super(eventBus);
		this.view = view;
		bind();
		initializeDFA();
	}

	private void bind() {
		viewHandlers.add(view.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				fireOnNewItemSelected(view.getSelectedItem());
			}
		}));

		viewHandlers.add(view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireHandleClickEventOnExitViewMode();
			}
		}));
	}

	public void deregisterFromView() {
		for (HandlerRegistration handler : viewHandlers) {
			handler.removeHandler();
		}
	}

	protected void initializeDFA() {
		dfa.addTransition("Init", "setItems", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				ItemSet itemSet = (ItemSet) parameters[0];
				handleItemSetChange(itemSet);
				ListBoxPresenter.this.completeItemSet = itemSet;
			}
		});
		TriggerParam triggerOnItemSetWithZeroOrOneItem = new TriggerParam() {
			public boolean matches(Object[] transitionParameters) {
				return ((ItemSet) transitionParameters[0]).size() <= 1;
			}
		};
		Transition goToDependendView = new Transition(triggerOnItemSetWithZeroOrOneItem, "DependendView", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				handleItemSetChange((ItemSet) parameters[0]);
				view.setDisplayMode(DependendView);
			}
		});
		dfa.addTransitionWithTriggerParam(new String[] { "Select", "DependendView" }, "setItems", goToDependendView);
		dfa.addTransition("Select", "setItems", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				handleItemSetChange((ItemSet) parameters[0]);
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
		Transition goToCreate = new Transition(new EqualsTriggerParam(Create), "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				itemSet = completeItemSet;
				view.setItems(completeItemSet.getItems());
				view.setDisplayMode(Create);
			}
		});
		dfa.addTransitionWithTriggerParam(new String[] { "Select", "DependendView", "Create", "View" }, "setDisplayMode", goToCreate);
		dfa.addTransition("View", "fireHandleClickEventOnExitViewMode", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				itemSet.removeSelection();
				view.setDisplayMode(Select);
				fireListBoxChangedEvent();
			}
		});
		dfa.addTransition("View", "setItems", "View", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				ItemSet itemSet = (ItemSet) parameters[0];
				handleItemSetChange(itemSet);
			}
		});
		dfa.addTransition("DependendView", "setItems", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				ItemSet itemSet = (ItemSet) parameters[0];
				handleItemSetChange(itemSet);
			}
		});
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

	private void handleItemSetChange(ItemSet itemSet) {
		this.itemSet = itemSet;
		view.setItems(itemSet.getItems());

		updateItemSetSelection();
		updateDisplayModeOfView();
	}

	private void updateItemSetSelection() {
		if (itemSet.size() == 1) {
			itemSet.setSelected(itemSet.getItem(0));
		}
		if (itemSet.getSelected() != null) {
			view.setSelectedItem(itemSet.getSelected());
		}
	}

	private void updateDisplayModeOfView() {
		if (itemSet.size() > 1) {
			view.setDisplayMode(Select);
		}
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

	public void setView(IListBoxView view) {
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
		dfa.transition(new Trigger("setDisplayMode", mode));
	}

	public Item getItem(int index) {
		return itemSet.getItem(index);
	}

	public IListBoxView getView() {
		return view;
	}

	public DisplayMode getDisplayMode() {
		if (dfa.getState() == "Select") {
			return Select;
		} else if (dfa.getState() == "Create") {
			return Create;
		} else if (dfa.getState() == "View") {
			return View;
		} else if (dfa.getState() == "DependendView") {
			return DependendView;
		}
		throw new IllegalStateException(dfa.getState() + " is not mappable to a display mode");
	}

	public ItemSet getCurrentItemSet() {
		return itemSet;
	}

	public void setItemSet(ItemSet itemSet) {
		dfa.transition(new Trigger("setItems", itemSet), itemSet);
	}

	public ItemSet getFullItemSet() {
		return completeItemSet;
	}

	public boolean isVisible() {
		return view.isVisible();
	}

	public boolean isInAViewMode() {
		DisplayMode mode = getDisplayMode();
		return mode == View || mode == DependendView;
	}

}
