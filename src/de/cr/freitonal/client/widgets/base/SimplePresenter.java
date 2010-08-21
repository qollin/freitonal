package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

public class SimplePresenter extends BasePresenter {
	private ListBoxPresenter listBoxPresenter;

	public interface View {
		public ListBoxPresenter.View getListBoxView();

		public HasText getLabel();
	}

	private View view;
	private DisplayMode mode = DisplayMode.Select;

	public SimplePresenter(HandlerManager eventBus, View view) {
		super(eventBus);
		this.view = view;
		this.listBoxPresenter = new SearchFieldPresenter(eventBus, view.getListBoxView());
	}

	public void setListBoxPresenter(ListBoxPresenter searchFieldPresenter) {
		this.listBoxPresenter = searchFieldPresenter;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setItems(ItemSet items) {
		listBoxPresenter.setItems(items);
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

	public void fireOnNewItemSelected(Item selectedItem) {
		listBoxPresenter.fireOnNewItemSelected(selectedItem);
	}

	public Item getItem(int index) {
		return listBoxPresenter.getItem(index);
	}

	public void setEnabled(boolean enabled) {
		listBoxPresenter.setEnabled(enabled);
	}

}
