package de.cr.freitonal.usertests.client.widgets.base;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;
import de.cr.freitonal.client.widgets.base.ListBoxView;

public class ListBoxPresenterTest extends GWTTestCase {

	private HandlerManager eventBus;
	private ListBoxPresenter.View view;
	final ArrayList<String> trace = new ArrayList<String>();

	@Override
	public void gwtSetUp() {
		eventBus = new HandlerManager(null);
		view = new ListBoxView();
		trace.clear();

		eventBus.addHandler(SearchFieldChangedEvent.TYPE, new SearchFieldChangedHandler() {
			public void onSearchFieldChanged(SearchFieldChangedEvent event) {
				trace.add("onSearchFieldChanged");
			}
		});
	}

	@Test
	public void testForCorrectDefaultValues() {
		ListBoxPresenter presenter = new ListBoxPresenter(eventBus, view);
		assertEquals("a presenter should start in Select mode", DisplayMode.Select, presenter.getDisplayMode());
	}

	@Test
	public void testConstructorWithDefaultEventHandlingStrategy() {
		ListBoxPresenter presenter = new ListBoxPresenter(eventBus, view);
		presenter.setItems(new ItemSet(Item.Empty));
		presenter.fireOnNewItemSelected_TEST(Item.Empty);
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
		presenter.fireOnNewItemSelected_TEST(Item.Empty);
		assertEquals("a global search event must not be generated", 1, trace.size());
		assertEquals("the event must be relayed to the locally registered handler", "onChangeEvent", trace.get(0));
	}

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

}
