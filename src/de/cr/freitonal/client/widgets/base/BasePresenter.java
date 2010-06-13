package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.Presenter;

public class BasePresenter implements Presenter {
	private final ListBoxPresenter listBoxPresenter;

	public interface View {
		public ListBoxPresenter.View getListBoxView();

		public HasText getLabel();
	}

	private View view;
	private final HandlerManager eventBus;
	private DisplayMode mode = DisplayMode.Select;

	public BasePresenter(HandlerManager eventBus, View view) {
		this.eventBus = eventBus;
		this.view = view;
		listBoxPresenter = new ListBoxPresenter(eventBus, view.getListBoxView());
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setItems(ItemSet items, SearchContext searchContext) {
		listBoxPresenter.setItems(items, searchContext);
	}

	public int getItemCount() {
		return listBoxPresenter.getItemCount();
	}

	/**
	 * @return the listBoxPresenter
	 */
	public ListBoxPresenter getListBoxPresenter() {
		return listBoxPresenter;
	}

	public DisplayMode getDisplayMode() {
		return mode;
	}

	public void setDisplayMode(DisplayMode mode) {
		this.mode = mode;
		listBoxPresenter.setDisplayMode(mode);
	}

	public Item getSelectedItem() {
		return listBoxPresenter.getSelectedItem();
	}

	public void fireOnNewItemSelected_TEST(Item selectedItem) {
		listBoxPresenter.fireOnNewItemSelected(selectedItem);
	}
}
