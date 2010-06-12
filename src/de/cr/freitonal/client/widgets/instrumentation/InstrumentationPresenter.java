package de.cr.freitonal.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.SearchContext.IntialLoading;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.AbstractTransitionAction;
import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.SearchContext;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SimpleDFA;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.ItemSetMultiSelection;
import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;

public class InstrumentationPresenter extends CompositePresenter {

	public interface View {
		HasClickHandlers getAddInstrumentButton();

		ListBoxPresenter.View addInstrumentList();
	}

	private final View view;
	private final SimpleDFA dfa = new SimpleDFA();
	private InstrumentationSet currentInstrumentationSet;
	private InstrumentationSet fullInstrumentationSet;

	public InstrumentationPresenter(HandlerManager eventBus, View view) {
		super(eventBus);
		this.view = view;

		addInstrumentPresenter();
		bind();
		initializeDFA();
	}

	private void initializeDFA() {
		dfa.addTransition("Initial", "setInstrumentations", "Main", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				fullInstrumentationSet = (InstrumentationSet) parameters[0];
				updateItemSetOfInstrument(getListBoxPresenter(0), (InstrumentationSet) parameters[0], IntialLoading);
			}
		});
		dfa.addTransition("Main", "setInstrumentations", "Main", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				updateItemSetOfInstrument(getListBoxPresenter(0), (InstrumentationSet) parameters[0], (SearchContext) parameters[1]);
			}
		});
		dfa.addTransition("Main", "instrumentSelected", "Search", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				search();
			}
		});
		dfa.addTransition("Search", "instrumentSelected", "Search", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				search();
			}
		});
		dfa.addTransition("Search", "setInstrumentations", "Search", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				ListBoxPresenter instrumentPresenter = addInstrumentPresenter();
				updateItemSetOfInstrument(instrumentPresenter, (InstrumentationSet) parameters[0], (SearchContext) parameters[1]);
			}
		});
		dfa.addTransition(new String[] { "Main", "Search" }, "setDisplayMode", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				DisplayMode mode = (DisplayMode) parameters[0];
				if (mode == Create) {
					removeAllButTheFirstListBoxPresenter();
				}
				InstrumentationPresenter.super.setDisplayMode(mode);
			}
		});
		dfa.addTransition("Create", "fireAddInstrumentButtonClicked", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				ListBoxPresenter instrumentPresenter = addInstrumentPresenter();
				updateItemSetOfInstrument(instrumentPresenter, fullInstrumentationSet, IntialLoading);
			}
		});
		dfa.start("Initial");
	}

	private ItemSet createBasicItemSetFromItemSetMultiSelection(ItemSetMultiSelection itemSet) {
		return new ItemSet(itemSet.getItems());
	}

	private void updateItemSetOfInstrument(ListBoxPresenter instrumentPresenter, InstrumentationSet instrumentationSet, SearchContext searchContext) {
		currentInstrumentationSet = instrumentationSet;
		ItemSet instruments = createBasicItemSetFromItemSetMultiSelection(instrumentationSet);
		instrumentPresenter.setItems(instruments, searchContext);
	}

	private void updateInstrumentationItemSet() {
		currentInstrumentationSet.setSelectedList(getSelectedItems());
	}

	private void search() {
		updateInstrumentationItemSet();
		eventBus.fireEvent(new SearchFieldChangedEvent());
	}

	private void bind() {
		view.getAddInstrumentButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireAddInstrumentButtonClicked();
			}
		});
	}

	private void fireAddInstrumentButtonClicked() {
		dfa.transition("fireAddInstrumentButtonClicked");
	}

	public void fireAddInstrumentButtonClicked_TEST() {
		fireAddInstrumentButtonClicked();
	}

	private ListBoxPresenter addInstrumentPresenter() {
		final ListBoxPresenter instrumentPresenter = createListBoxPresenter(view.addInstrumentList());

		instrumentPresenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				dfa.transition("instrumentSelected");
			}
		});

		return instrumentPresenter;
	}

	public void setInstrumentations(InstrumentationSet instrumentationSet, SearchContext context) {
		dfa.transition("setInstrumentations", instrumentationSet, context);
	}

	public ListBoxPresenter getInstrumentPresenter(int i) {
		return getListBoxPresenter(i);
	}

	public int getNumberOfInstrumentPresenters() {
		return getNumberOfListBoxPresenters();
	}

	@Override
	public void setDisplayMode(DisplayMode mode) {
		dfa.transition("setDisplayMode", mode);
	}
}
