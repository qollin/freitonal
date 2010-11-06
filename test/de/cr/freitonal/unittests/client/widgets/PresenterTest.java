package de.cr.freitonal.unittests.client.widgets;

import java.util.ArrayList;

import org.junit.Before;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;

public class PresenterTest {
	protected final EventBus eventBus = new SimpleEventBus();
	protected final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void setup() {
		eventBus.addHandler(SearchFieldChangedEvent.TYPE, new SearchFieldChangedHandler() {
			public void onSearchFieldChanged(SearchFieldChangedEvent event) {
				trace.add("search");
			}
		});
		trace.clear();
	}
}
