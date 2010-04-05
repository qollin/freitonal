package de.cr.freitonal.tests.client.widgets.base;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.event.SearchFieldChangedEvent;
import de.cr.freitonal.client.event.SearchFieldChangedHandler;
import de.cr.freitonal.client.widgets.base.BasePresenter;

public class BasePresenterTest extends EasyMockSupport {
	private BasePresenter.View displayMock;
	private HandlerManager eventBus;
	private SearchFieldChangedHandler handlerMock;

	@Before
	public void setUp() {
		displayMock = createStrictMock(BasePresenter.View.class);
		handlerMock = createStrictMock(SearchFieldChangedHandler.class);
		eventBus = new HandlerManager(null);
	}

	@Test
	public void testWiring() {
		//expect(displayMock.getListBox()).andReturn(createStrictMock(HasChangeHandlers.class));
		replay(displayMock);
		new BasePresenter(eventBus, displayMock);
		verify(displayMock);
	}

	@Test
	public void testSearchFieldChange() {
		eventBus.addHandler(SearchFieldChangedEvent.TYPE, handlerMock);
		/*
		expect(displayMock.getListBox()).andReturn(createStrictMock(HasChangeHandlers.class));
		handlerMock.onSearchFieldChanged((SearchFieldChangedEvent) anyObject());

		replay(displayMock);
		replay(handlerMock);

		BasePresenter basePresenter = new BasePresenter(eventBus, displayMock);
		basePresenter.onChangeEvent(null);
		*/
		verify(displayMock);
		verify(handlerMock);
	}
}
