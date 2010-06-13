package de.cr.freitonal.unittests.client.widgets.base;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;

public class ListBoxPresenterTest {

	private HandlerManager eventBus;
	private ListBoxPresenter.View view;
	final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void setUp() {
		eventBus = new HandlerManager(null);
		view = new ListBoxViewMock();
		trace.clear();

		eventBus.addHandler(SearchFieldChangedEvent.TYPE, new SearchFieldChangedHandler() {
			public void onSearchFieldChanged(SearchFieldChangedEvent event) {
				trace.add("onSearchFieldChanged");
			}
		});
	}

	@Test
	public void testConstructorWithDefaultEventHandlingStrategy() {
		ListBoxPresenter presenter = new ListBoxPresenter(eventBus, view);
		presenter.setItems(new ItemSet(Item.Empty));
		presenter.fireOnNewItemSelected(Item.Empty);
		assertEquals(1, trace.size());
		assertEquals("onSearchFieldChanged", trace.get(0));

	}

	@Test
	public void testConstructorWithRelayingEventHandlingStrategy() {
		ListBoxPresenter presenter = new ListBoxPresenter(eventBus, view, ListBoxPresenter.EventHandlingStrategy.RelayToRegisteredChangeEventHandlers);
		presenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				trace.add("onChangeEvent");
			}
		});
		presenter.setItems(new ItemSet(Item.Empty));
		presenter.fireOnNewItemSelected(Item.Empty);
		assertEquals("a global search event must not be generated", 1, trace.size());
		assertEquals("the event must be relayed to the locally registered handler", "onChangeEvent", trace.get(0));
	}

}
