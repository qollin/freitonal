package de.cr.freitonal.client.widgets.catalog;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;

public class CatalogPresenter {
	private final View view;
	private final HandlerManager eventBus;
	private final ListBoxPresenter nameListBoxPresenter;
	private final ListBoxPresenter numberListBoxPresenter;

	public interface View {
		public ListBoxPresenter.View getNameListBoxView();

		public ListBoxPresenter.View getNumberListBoxView();
	}

	public CatalogPresenter(HandlerManager eventBus, View view) {
		this.eventBus = eventBus;
		this.view = view;

		nameListBoxPresenter = new ListBoxPresenter(eventBus, view.getNameListBoxView());
		numberListBoxPresenter = new ListBoxPresenter(eventBus, view.getNumberListBoxView());
	}

	public void setCatalogs(CatalogSet catalogs) {
		nameListBoxPresenter.setItems(catalogs.getNames());
		numberListBoxPresenter.setItems(catalogs.getNumbers());
	}

	public int getNameItemCount() {
		return nameListBoxPresenter.getItemCount();
	}

	public int getNumberItemCount() {
		return numberListBoxPresenter.getItemCount();
	}
}
