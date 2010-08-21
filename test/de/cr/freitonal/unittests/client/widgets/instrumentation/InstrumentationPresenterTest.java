package de.cr.freitonal.unittests.client.widgets.instrumentation;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.PiecePlusInstrumentationTypeSelectedEvent;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.widgets.instrumentation.InstrumentationPresenter;
import de.cr.freitonal.shared.models.Item;

public class InstrumentationPresenterTest {
	private InstrumentationPresenter instrumentationPresenter;

	private static Item Piano = new Item("1", "Piano");
	private static Item Violin = new Item("2", "Violin");
	private static Item Viola = new Item("3", "Viola");
	private static Item Violoncello = new Item("4", "Violoncello");

	private InstrumentationSet instrumentationSet;
	private HandlerManager eventBus;

	@Before
	public void setUp() {
		eventBus = new HandlerManager(null);

		InstrumentationPresenter.View view = new InstrumentationViewMock();
		instrumentationPresenter = new InstrumentationPresenter(eventBus, view);
		instrumentationSet = new InstrumentationSet(Piano, Violin, Viola, Violoncello);

		instrumentationPresenter.setInstrumentations(instrumentationSet);
	}

	@Test
	public void testInitialLoading() {
		assertEquals(4, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
	}

	@Test
	public void testSelectionOfOneInstrument() {
		//there are no listeners on the event bus, but this will create all necessary side effects on the view side
		instrumentationPresenter.getInstrumentPresenter(0).fireOnNewItemSelected(Piano);

		instrumentationPresenter.setInstrumentations(new InstrumentationSet(Violin, Violoncello));
		assertEquals("There should be 2 dropdown boxes", 2, instrumentationPresenter.getNumberOfInstrumentPresenters());
		assertEquals("The first dropdown box should have 1 item", 1, instrumentationPresenter.getInstrumentPresenter(0).getItemCount());
		assertEquals("The first dropdown box should have the piano selected", Piano, instrumentationPresenter.getInstrumentPresenter(0)
				.getSelectedItem());
		assertEquals("The second dropdown box should have two items", 2, instrumentationPresenter.getInstrumentPresenter(1).getItemCount());
		assertEquals("One item should be selected in the item set", 1, instrumentationSet.getSelectedList().size());
		assertEquals("The selected item in the item set should be the piano", Piano, instrumentationSet.getSelectedList().get(0));
	}

	@Test
	public void testReactingToAPieceAndInstrumentationTypeBeingSelected() {
		assertTrue(instrumentationPresenter.isEnabled());

		eventBus.fireEvent(new PiecePlusInstrumentationTypeSelectedEvent());

		assertFalse("after an piecePlusInstrumentationType was selected, the instrumentation should be disabled",
					instrumentationPresenter.isEnabled());
	}
}
