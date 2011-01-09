package de.cr.freitonal.client.widgets.instrumentation;

import static de.cr.freitonal.client.event.DisplayMode.Create;

import java.util.HashSet;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedHandler;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.dfa.AbstractTransitionAction;
import de.cr.freitonal.client.event.dfa.DFA;
import de.cr.freitonal.client.event.dfa.Transition;
import de.cr.freitonal.client.event.dfa.Trigger;
import de.cr.freitonal.client.event.dfa.TriggerParam;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.SelectablePresenter;
import de.cr.freitonal.client.widgets.base.listbox.IListBoxView;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class InstrumentationPresenter extends CompositePresenter {

	public interface View {
		HasClickHandlers getAddInstrumentButton();

		IListBoxView addInstrumentList();

		void setAddInstrumentButtonVisible(boolean visible);
	}

	private final View view;
	private final DFA dfa = new DFA();
	private InstrumentationSet fullInstrumentationSet;
	private boolean enabled = true;

	public InstrumentationPresenter(EventBus eventBus, View view) {
		super(eventBus);
		this.view = view;

		addInstrumentPresenter();
		bind();
		initializeDFA();

		view.setAddInstrumentButtonVisible(false);
	}

	private void initializeDFA() {
		dfa.addTransition("Initial", "setInstrumentations", "Main", new AbstractTransitionAction() {
			@Override
			public void onTransition(Object[] parameters) {
				fullInstrumentationSet = (InstrumentationSet) parameters[0];
				updateItemSetOfInstrument(getPresenter(0), (InstrumentationSet) parameters[0]);
			}
		});
		TriggerParam triggerOnInstrumentationSetWithZeroOrOneItem = new TriggerParam() {
			public boolean matches(Object[] transitionParameters) {
				return ((InstrumentationSet) transitionParameters[0]).size() <= 1;
			}
		};
		Transition goToDependendendView = new Transition(triggerOnInstrumentationSetWithZeroOrOneItem, "DependendView",
				new AbstractTransitionAction() {
					@Override
					public void onTransition(Object[] parameters) {
						setDisplayItemOnFirstPresenter((InstrumentationSet) parameters[0]);
						hideAddInstrumentButton();
					}
				});
		dfa.addTransitionWithTriggerParam("Main", "setInstrumentations", goToDependendendView);
		dfa.addTransition(new String[] { "Main", "DependendView" }, "setInstrumentations", "Main", new AbstractTransitionAction() {
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
		dfa.addTransition(new String[] { "Main", "Search", "DependendView", "View" }, "setDisplayMode", "Create", new AbstractTransitionAction() {
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

	private void hideAddInstrumentButton() {
		view.setAddInstrumentButtonVisible(false);
	}

	private void setDisplayItemOnFirstPresenter(InstrumentationSet instrumentationSet) {
		Instrumentation instrumentation = instrumentationSet.getInstrumentations().get(0);

		Item displayItem = new Item(instrumentation.getID(), instrumentation.toString());
		getPresenter(0).setItemSet(new ItemSet(displayItem));
	}

	private void updateItemSetOfInstrument(SelectablePresenter instrumentPresenter, InstrumentationSet instrumentationSet) {
		ItemSet instruments = extractInstrumentsFromInstrumentationSet(instrumentationSet);
		instrumentPresenter.setItemSet(instruments);
	}

	private ItemSet extractInstrumentsFromInstrumentationSet(InstrumentationSet instrumentationSet) {
		HashSet<Item> instruments = new HashSet<Item>();
		for (Instrumentation instrumentation : instrumentationSet.getInstrumentations()) {
			instruments.addAll(instrumentation.getInstruments());
		}

		return new ItemSet(instruments);
	}

	private void search() {
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
		dfa.transition(new Trigger("setInstrumentations", instrumentationSet), instrumentationSet);
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

	public DFA getDFA() {
		return dfa;
	}

}
