package de.cr.freitonal.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.Create;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedHandler;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.ItemSetMultiSelection;
import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.client.widgets.base.SelectablePresenter;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class InstrumentationPresenter extends CompositePresenter {

	public interface View {
		HasClickHandlers getAddInstrumentButton();

		SearchFieldPresenter.View addInstrumentList();
	}

	private final View view;
	private final DFA dfa = new DFA();
	private InstrumentationSet currentInstrumentationSet;
	private InstrumentationSet fullInstrumentationSet;
	private boolean enabled = true;

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
				updateItemSetOfInstrument(getPresenter(0), (InstrumentationSet) parameters[0]);
			}
		});
		dfa.addTransition("Main", "setInstrumentations", "Main", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				updateItemSetOfInstrument(getPresenter(0), (InstrumentationSet) parameters[0]);
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
				updateItemSetOfInstrument(instrumentPresenter, (InstrumentationSet) parameters[0]);
			}
		});
		dfa.addTransition(new String[] { "Main", "Search" }, "setDisplayMode", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				DisplayMode mode = (DisplayMode) parameters[0];
				if (mode == Create) {
					removeAllButTheFirstPresenter();
				}
				InstrumentationPresenter.super.setDisplayMode(mode);
			}
		});
		dfa.addTransition("Create", "fireAddInstrumentButtonClicked", "Create", new AbstractTransitionAction() {
			@Override
			public void onTransition() {
				ListBoxPresenter instrumentPresenter = addInstrumentPresenter();
				updateItemSetOfInstrument(instrumentPresenter, fullInstrumentationSet);
			}
		});
		dfa.addTransition("Create", "instrumentSelected", "Create");
		dfa.start("Initial");
	}

	private ItemSet createBasicItemSetFromItemSetMultiSelection(ItemSetMultiSelection itemSet) {
		return new ItemSet(itemSet.getItems());
	}

	private void updateItemSetOfInstrument(SelectablePresenter instrumentPresenter, InstrumentationSet instrumentationSet) {
		currentInstrumentationSet = instrumentationSet;
		ItemSet instruments = createBasicItemSetFromItemSetMultiSelection(instrumentationSet);
		instrumentPresenter.setItems(instruments);
	}

	private void updateInstrumentationItemSet() {
		currentInstrumentationSet.setSelectedList(getSelectedItems());
	}

	private void search() {
		updateInstrumentationItemSet();
		getEventBus().fireEvent(new SearchFieldChangedEvent());
	}

	private void bind() {
		view.getAddInstrumentButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fireAddInstrumentButtonClicked();
			}
		});
		getEventBus().addHandler(PiecePlusInstrumentationTypeSelectedEvent.TYPE, new PiecePlusInstrumentationTypeSelectedHandler() {
			public void onPiecePlusInstrumentationTypeSelected() {
				setEnabled(false);
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
		final ListBoxPresenter instrumentPresenter = new ListBoxPresenter(getEventBus(), view.addInstrumentList());
		addPresenter(instrumentPresenter);

		instrumentPresenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				dfa.transition("instrumentSelected");
			}
		});

		return instrumentPresenter;
	}

	public void setInstrumentations(InstrumentationSet instrumentationSet) {
		dfa.transition("setInstrumentations", instrumentationSet);
	}

	public ListBoxPresenter getInstrumentPresenter(int i) {
		return (ListBoxPresenter) getPresenter(i);
	}

	public int getNumberOfInstrumentPresenters() {
		return getNumberOfPresenters();
	}

	@Override
	public void setDisplayMode(DisplayMode mode) {
		dfa.transition("setDisplayMode", mode);
	}

	public VolatileInstrumentation getSelectedItem() {
		return new VolatileInstrumentation("", getSelectedItems());
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		for (SelectablePresenter presenter : getListBoxPresenters()) {
			presenter.setEnabled(enabled);
		}
	}
}
