package de.cr.freitonal.unittests.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.Create;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.widgets.base.listbox.EditableSearchFieldPresenter;
import de.cr.freitonal.shared.models.VolatileItem;

public class AnEditableSearchFieldPresenterInCreateModeShould extends EditableSearchFieldPresenterTest {

	private EditableListBoxViewMock view;
	private EditableSearchFieldPresenter editableSearchFieldPresenter;

	@Before
	public void setupEditableSearchFieldPresenterInCreateMode() {
		view = new EditableListBoxViewMock(trace);
		editableSearchFieldPresenter = new EditableSearchFieldPresenter(eventBus, view);

		editableSearchFieldPresenter.setItemSet(twoElementItemSet);
		editableSearchFieldPresenter.setDisplayMode(Create);
	}

	@Test
	public void ReturnTheEnteredTextAsTheSelectedItem() {
		editableSearchFieldPresenter.setEnteredText("27-1");
		VolatileItem item = editableSearchFieldPresenter.getSelectedItem();
		assertEquals("27-1", item.getValue());
	}
}
