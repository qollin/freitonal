package de.cr.freitonal.client.widgets.composer;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.widgets.base.scalar.ScalarPresenter;

public class ComposerPresenter extends ScalarPresenter {

	public interface View extends ScalarPresenter.View {

	}

	public ComposerPresenter(HandlerManager eventBus, View view) {
		super(eventBus, view);
	}
}
