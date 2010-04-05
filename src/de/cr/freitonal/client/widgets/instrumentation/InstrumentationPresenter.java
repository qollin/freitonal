package de.cr.freitonal.client.widgets.instrumentation;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;
import de.cr.freitonal.client.widgets.event.SimpleDFA;

public class InstrumentationPresenter {

	public interface View {
		ListBoxPresenter.View getInstrumentView(int i);

		HasClickHandlers getAddInstrumentButton();

		ListBoxPresenter.View addInstrumentList();

	}

	private final View view;
	private final ArrayList<ListBoxPresenter> listBoxPresenters = new ArrayList<ListBoxPresenter>();
	private final HandlerManager eventBus;
	private boolean initializationDone = false;
	private final SimpleDFA dfa = new SimpleDFA();

	public InstrumentationPresenter(HandlerManager eventBus, View view) {
		this.eventBus = eventBus;
		this.view = view;

		addInstrumentListBox();
		bind();
		initializeDFA();
	}

	private void initializeDFA() {
		dfa.addTransition("Init", "setInstrumentations", "Main");
	}

	private void bind() {
		view.getAddInstrumentButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addInstrumentListBox();
			}
		});
	}

	private void addInstrumentListBox() {
		ListBoxPresenter listBoxPresenter = new ListBoxPresenter(eventBus, view.addInstrumentList());
		listBoxPresenters.add(listBoxPresenter);
	}

	public void setInstrumentations(InstrumentationSet instrumentations) {
		if (!initializationDone) {
			listBoxPresenters.get(0).setItems(instrumentations);
			initializationDone = true;
		} else {
			addInstrumentListBox();

			//set the new Items in the new ListBox:
			listBoxPresenters.get(listBoxPresenters.size() - 1).setItems(instrumentations);

			//set the old ListBox just to the selected Item
			listBoxPresenters.get(listBoxPresenters.size() - 2).setItems(instrumentations.getSelected());
		}
	}

	public int getItemCount(int i) {
		return listBoxPresenters.get(i).getItemCount();
	}

	public ListBoxPresenter getListBoxPresenter(int i) {
		return listBoxPresenters.get(i);
	}
}
