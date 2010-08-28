package de.cr.freitonal.unittests.client.widgets.base;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.View;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter;
import de.cr.freitonal.shared.models.Item;

public class ListBoxPresenterTest {

	protected HandlerManager eventBus;
	protected ListBoxPresenter.View view;
	protected final ArrayList<String> trace = new ArrayList<String>();
	protected final Item emptyItem = new Item("", "");
	private ListBoxPresenter presenter;

	@Before
	public void setUp() {
		eventBus = new HandlerManager(null);
		view = new ListBoxViewMock(trace);
		trace.clear();

		eventBus.addHandler(SearchFieldChangedEvent.TYPE, new SearchFieldChangedHandler() {
			public void onSearchFieldChanged(SearchFieldChangedEvent event) {
				trace.add("onSearchFieldChanged");
			}
		});
		presenter = new ListBoxPresenter(eventBus, view);
		presenter.setItems(new ItemSet(emptyItem));
	}

	@Test
	public void testConstructor() {
		presenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				trace.add("onChangeEvent");
			}
		});
		presenter.fireOnNewItemSelected(emptyItem);
		assertFalse("a global search event must not be generated", trace.contains("onSearchFieldChanged"));
	}

	@Test
	public void testSetDisplayMode() {
		presenter.setDisplayMode(Create);
		assertEquals("setDisplayMode must have been called on the view", 1, trace.size());
		assertEquals("setDisplayMode must have been called on the view", "setDisplayMode:" + Create, trace.get(0));
	}

	@Test
	public void WhenOnlyASingleItemIsAvailableSwitchToViewMode() {
		presenter.setItems(new ItemSet(emptyItem));
		assertEquals(View, presenter.getDisplayMode());
	}
}
