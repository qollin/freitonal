package de.cr.freitonal.client.widgets.catalog;

import static de.cr.freitonal.client.event.DisplayMode.Create;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.EqualsTriggerParam;
import de.cr.freitonal.client.event.dfa.Trigger;
import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.listbox.EditableSearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.listbox.IEditableListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileCatalog;

public class CatalogPresenter extends CompositePresenter {
	private final View view;
	private final ListBoxPresenter nameListBoxPresenter;
	private final EditableSearchFieldPresenter numberListBoxPresenter;
	private final DFA dfa = new DFA();

	public interface View {
		public IListBoxView getNameListBoxView();

		public IEditableListBoxView getNumberListBoxView();
	}

	public CatalogPresenter(EventBus eventBus, View view) {
		super(eventBus);
		this.view = view;

		nameListBoxPresenter = new SearchFieldPresenter(eventBus, view.getNameListBoxView());
		addPresenter(nameListBoxPresenter);
		numberListBoxPresenter = new EditableSearchFieldPresenter(eventBus, view.getNumberListBoxView());
		addPresenter(numberListBoxPresenter);

		numberListBoxPresenter.setEnabled(false);

		bind();
		initializeDFA();
	}

	private void bind() {
		nameListBoxPresenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				dfa.transition("nameListBoxChanged");
			}
		});
	}

	private void initializeDFA() {
		dfa.addTransition("Select", "nameListBoxChanged", "Select", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				numberListBoxPresenter.setEnabled(true);
			}
		});
		dfa.addTransitionWithTriggerParam("Select", "setDisplayMode", new EqualsTriggerParam(Create), "Create");
		dfa.addTransition("Create", "nameListBoxChanged", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				numberListBoxPresenter.setEnabled(true);
			}
		});

		dfa.start("Select");
	}

	public void setCatalogs(CatalogSet catalogs) {
		nameListBoxPresenter.setItemSet(catalogs.getNames());

		if (nameListBoxPresenter.isInAViewMode()) {
			numberListBoxPresenter.setEnabled(true);
		}

		numberListBoxPresenter.setItemSet(catalogs.getNumbers());
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

	public EditableSearchFieldPresenter getNumberListBoxPresenter() {
		return numberListBoxPresenter;
	}

	public void fireOnNewItemSelected(Catalog catalog) {
		nameListBoxPresenter.fireOnNewItemSelected(catalog.getCatalogName());
		numberListBoxPresenter.setEnteredText(catalog.getCatalogNumber().getValue());
	}

	public Catalog getSelectedItem() {
		if (getDisplayMode() == Create) {
			throw new IllegalStateException("in create mode only a volatile catalog is available; call getEnteredItem");
		}

		ArrayList<Item> items = getSelectedItems();

		Item name = items.get(0);
		Item number = items.get(1);

		return new Catalog(name, number);
	}

	public VolatileCatalog getEnteredItem() {
		Item catalogName = nameListBoxPresenter.getSelectedItem();
		String ordinal = numberListBoxPresenter.getText();
		return new VolatileCatalog(catalogName, ordinal);
	}

	@Override
	public void setDisplayMode(DisplayMode mode) {
		super.setDisplayMode(mode);
		dfa.transition(new Trigger("setDisplayMode", mode));
	};
}
