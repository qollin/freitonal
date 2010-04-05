package de.cr.freitonal.client.widgets.composer;

import com.google.gwt.event.shared.HandlerManager;

import de.cr.freitonal.client.widgets.base.BasePresenter;

public class ComposerPresenter extends BasePresenter {

	public interface View extends BasePresenter.View {

	}

	public ComposerPresenter(HandlerManager eventBus, View view) {
		super(eventBus, view);
	}
}
