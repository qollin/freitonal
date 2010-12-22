package de.cr.freitonal.unittests.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.DependendView;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter;

public class AListBoxPresenterAfterInitialLoadingShould extends ListBoxPresenterTest {
	private ListBoxPresenter listBoxPresenter;

	@Before
	public void setupListBoxPresenterAfterInitialLoading() {
		listBoxPresenter = new ListBoxPresenter(eventBus, view);
		listBoxPresenter.setItemSet(twoElementItemSet);
	}

	@Test
	public void CallSetDisplayModeWhenGivenAnEmptyItemSet() {
		listBoxPresenter.setItemSet(new ItemSet());
		assertTrue(trace + " does not contain the call to setDisplayMode", trace.contains("setDisplayMode:" + DependendView));
	}

	@Test
	public void CallSetDisplayModeWhenGivenASingleItemItemSet() {
		listBoxPresenter.setItemSet(oneElementItemSet);

		assertTrue(trace + " does not contain the call to setDisplayMode", trace.contains("setDisplayMode:" + DependendView));
	}

	@Test
	public void NotTriggerASearchEventWhenAnItemIsSelected() {
		listBoxPresenter.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				trace.add("onChangeEvent");
			}
		});
		listBoxPresenter.fireOnNewItemSelected(firstItem);
		assertFalse("a global search event must not be generated", trace.contains("onSearchFieldChanged"));
		assertTrue(trace.contains("onChangeEvent"));
	}
}
