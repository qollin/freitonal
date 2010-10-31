package de.cr.freitonal.client.widgets.base;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.shared.models.Item;

public abstract class CompositePresenter extends BasePresenter {
	private final ArrayList<SelectablePresenter> presenters = new ArrayList<SelectablePresenter>();
	private final ArrayList<View> views = new ArrayList<View>();

	protected CompositePresenter(HandlerManager eventBus) {
		super(eventBus);
	}

	protected void addPresenter(SelectablePresenter presenter) {
		presenters.add(presenter);
		views.add(presenter.getView());
	}

	protected void removePresenter(int index) {
		presenters.remove(index);
		views.get(index).removeFromParent();
		views.remove(index);
	}

	protected SelectablePresenter getPresenter(int index) {
		return presenters.get(index);
	}

	protected int getNumberOfPresenters() {
		return presenters.size();
	}

	protected ArrayList<SelectablePresenter> getListBoxPresenters() {
		return presenters;
	}

	protected void removeAllButTheFirstPresenter() {
		while (getNumberOfPresenters() > 1) {
			removePresenter(1);
		}
	}

	public void setDisplayMode(DisplayMode mode) {
		for (SelectablePresenter presenter : presenters) {
			presenter.setDisplayMode(mode);
		}
	}

	public ArrayList<Item> getSelectedItems() {
		ArrayList<Item> selectedItems = new ArrayList<Item>();
		for (SelectablePresenter presenter : presenters) {
			if (presenter.getSelectedItem() != null) {
				selectedItems.add(presenter.getSelectedItem());
			}
		}
		return selectedItems;
	}

	public void setEnabled(boolean enabled) {
		for (SelectablePresenter presenter : presenters) {
			presenter.setEnabled(enabled);
		}
	}

	public DisplayMode getDisplayMode() {
		DisplayMode mode = presenters.get(0).getDisplayMode();
		for (SelectablePresenter presenter : presenters) {
			if (presenter.getDisplayMode() != mode) {
				throw new IllegalStateException("CompositePresenter cannot deal with sub presenters in different display modes");
			}
		}
		return mode;
	}

}
