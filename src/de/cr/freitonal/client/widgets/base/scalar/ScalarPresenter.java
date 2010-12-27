package de.cr.freitonal.client.widgets.base.scalar;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.BasePresenter;
import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.shared.models.Item;

public class ScalarPresenter extends BasePresenter {
	private ListBoxPresenter listBoxPresenter;

	public interface View {
		public IListBoxView getListBoxView();

		public HasText getLabel();
	}

	@SuppressWarnings("unused")
	private View view;

	public ScalarPresenter(EventBus eventBus, View view) {
		super(eventBus);
		this.view = view;
		this.listBoxPresenter = new SearchFieldPresenter(eventBus, view.getListBoxView());
	}

	public void setListBoxPresenter(ListBoxPresenter listBoxPresenter) {
		this.listBoxPresenter.deregisterFromView();
		this.listBoxPresenter = listBoxPresenter;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setItems(ItemSet items) {
		listBoxPresenter.setItemSet(items);
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
		return listBoxPresenter.getDisplayMode();
	}

	public void setDisplayMode(DisplayMode mode) {
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
