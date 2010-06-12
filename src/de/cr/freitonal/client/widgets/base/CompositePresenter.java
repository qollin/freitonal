package de.cr.freitonal.client.widgets.base;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.widgets.Presenter;

public abstract class CompositePresenter implements Presenter {
	protected final HandlerManager eventBus;
	private final ArrayList<ListBoxPresenter> presenters = new ArrayList<ListBoxPresenter>();
	private final ArrayList<ListBoxPresenter.View> views = new ArrayList<ListBoxPresenter.View>();

	protected CompositePresenter(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	protected ListBoxPresenter createListBoxPresenter(ListBoxPresenter.View view) {
		ListBoxPresenter listBoxPresenter = new ListBoxPresenter(eventBus, view,
				ListBoxPresenter.EventHandlingStrategy.RelayToRegisteredChangeEventHandlers);
		presenters.add(listBoxPresenter);
		views.add(view);
		return listBoxPresenter;
	}

	protected void removeListBoxPresenter(int index) {
		presenters.remove(index);
		views.get(index).removeFromParent();
		views.remove(index);
	}

	protected ListBoxPresenter getListBoxPresenter(int index) {
		return presenters.get(index);
	}

	protected int getNumberOfListBoxPresenters() {
		return presenters.size();
	}

	protected void removeAllButTheFirstListBoxPresenter() {
		while (getNumberOfListBoxPresenters() > 1) {
			removeListBoxPresenter(1);
		}
	}

	public void setDisplayMode(DisplayMode mode) {
		for (ListBoxPresenter presenter : presenters) {
			presenter.setDisplayMode(mode);
		}
	}

	protected ArrayList<Item> getSelectedItems() {
		ArrayList<Item> selectedItems = new ArrayList<Item>();
		for (ListBoxPresenter presenter : presenters) {
			selectedItems.add(presenter.getSelectedItem());
		}
		return selectedItems;
	}

}
