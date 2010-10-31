package de.cr.freitonal.unittests.client.widgets.base;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static de.cr.freitonal.client.event.DisplayMode.View;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.widgets.base.CompositePresenter;
import de.cr.freitonal.client.widgets.base.SelectablePresenter;

public class ACompositePresenterWithTwoSubPresentersShould {
	private final HandlerManager eventBus = new HandlerManager(null);
	private CompositePresenter compositePresenter;
	private final SelectablePresenter selectablePresenter1 = new SelectablePresenterMock();
	private final SelectablePresenter selectablePresenter2 = new SelectablePresenterMock();

	@Before
	public void setupCompositePresenter() {
		compositePresenter = new CompositePresenterWithTwoSubPresenters();
	}

	@Test
	public void ReturnTheDisplayModeOfItsSubPresenters() {
		selectablePresenter1.setDisplayMode(View);
		selectablePresenter2.setDisplayMode(View);
		assertEquals(View, compositePresenter.getDisplayMode());
	}

	@Test
	public void ThrowAnExceptionIfTheDisplayModesOfItsSubPresentersAreNotEqual() {
		try {
			selectablePresenter1.setDisplayMode(View);
			selectablePresenter2.setDisplayMode(Create);
			compositePresenter.getDisplayMode();
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void OnlyIncludeSelectedItemsWhenReturningTheSelection() {
		assertEquals(0, compositePresenter.getSelectedItems().size());
	}

	private class CompositePresenterWithTwoSubPresenters extends CompositePresenter {
		public CompositePresenterWithTwoSubPresenters() {
			super(eventBus);
			addPresenter(selectablePresenter1);
			addPresenter(selectablePresenter2);
		}
	}
}
