package de.cr.freitonal.client.widgets.base;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;

import de.cr.freitonal.client.models.ItemSet;

public class BasePresenter {
	private final ListBoxPresenter listBoxPresenter;

	public interface View {
		public ListBoxPresenter.View getListBoxView();

		public HasText getLabel();
	}

	private View view;
	private final HandlerManager eventBus;

	public BasePresenter(HandlerManager eventBus, View view) {
		this.eventBus = eventBus;
		this.view = view;
		listBoxPresenter = new ListBoxPresenter(eventBus, view.getListBoxView());
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
}
