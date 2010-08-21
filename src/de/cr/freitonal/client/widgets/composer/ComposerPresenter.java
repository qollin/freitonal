package de.cr.freitonal.client.widgets.composer;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.widgets.base.SimplePresenter;

public class ComposerPresenter extends SimplePresenter {

	public interface View extends SimplePresenter.View {

	}

	public ComposerPresenter(HandlerManager eventBus, View view) {
		super(eventBus, view);
	}
}
