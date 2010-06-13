package de.cr.freitonal.client.widgets.catalog;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.models.Catalog;
import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;

public class CatalogPresenter extends CompositePresenter {
	private final View view;
	private final ListBoxPresenter nameListBoxPresenter;
	private final ListBoxPresenter numberListBoxPresenter;

	public interface View {
		public ListBoxPresenter.View getNameListBoxView();

		public ListBoxPresenter.View getNumberListBoxView();
	}

	public CatalogPresenter(HandlerManager eventBus, View view) {
		super(eventBus);
		this.view = view;

		nameListBoxPresenter = createListBoxPresenter(view.getNameListBoxView());
		nameListBoxPresenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				CatalogPresenter.this.eventBus.fireEvent(new SearchFieldChangedEvent());
				numberListBoxPresenter.setEnabled(true);
			}
		});
		numberListBoxPresenter = createListBoxPresenter(view.getNumberListBoxView());
		numberListBoxPresenter.setEnabled(false);
	}

	public void setCatalogs(CatalogSet catalogs, SearchContext searchContext) {
		nameListBoxPresenter.setItems(catalogs.getNames(), searchContext);
		numberListBoxPresenter.setItems(catalogs.getNumbers(), searchContext);
	}

	public int getNameItemCount() {
		return nameListBoxPresenter.getItemCount();
	}

	public ListBoxPresenter getNameListBoxPresenter() {
		return nameListBoxPresenter;
	}

	public int getNumberItemCount() {
		return numberListBoxPresenter.getItemCount();
	}

	public ListBoxPresenter getNumberListBoxPresenter() {
		return numberListBoxPresenter;
	}

	public void fireOnNewItemSelected(Catalog catalog) {
		nameListBoxPresenter.fireOnNewItemSelected(catalog.name);
		numberListBoxPresenter.fireOnNewItemSelected(catalog.number);
	}

	public Catalog getSelectedItem() {
		ArrayList<Item> items = getSelectedItems();

		return new Catalog(items.get(0), items.get(1));
	}

}
