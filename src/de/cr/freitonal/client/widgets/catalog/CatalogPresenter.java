package de.cr.freitonal.client.widgets.catalog;

import static de.cr.freitonal.client.event.DisplayMode.Create;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.EqualsTriggerParam;
import de.cr.freitonal.client.event.dfa.Trigger;
import de.cr.freitonal.client.models.Catalog;
import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.shared.models.Item;

public class CatalogPresenter extends CompositePresenter {
	private final View view;
	private final ListBoxPresenter nameListBoxPresenter;
	private final ListBoxPresenter numberListBoxPresenter;
	private final DFA dfa = new DFA();

	public interface View {
		public ListBoxPresenter.View getNameListBoxView();

		public ListBoxPresenter.View getNumberListBoxView();
	}

	public CatalogPresenter(HandlerManager eventBus, View view) {
		super(eventBus);
		this.view = view;

		nameListBoxPresenter = new SearchFieldPresenter(eventBus, view.getNameListBoxView());
		addPresenter(nameListBoxPresenter);
		numberListBoxPresenter = new SearchFieldPresenter(eventBus, view.getNumberListBoxView());
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
		nameListBoxPresenter.setItems(catalogs.getNames());
		numberListBoxPresenter.setItems(catalogs.getNumbers());
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

	@Override
	public void setDisplayMode(DisplayMode mode) {
		super.setDisplayMode(mode);
		dfa.transition(new Trigger("setDisplayMode", mode));
	};
}
