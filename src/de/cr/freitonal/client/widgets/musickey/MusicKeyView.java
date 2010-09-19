package de.cr.freitonal.client.widgets.musickey;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.scalar.ScalarView;

public class MusicKeyView extends ScalarView implements MusicKeyPresenter.View {

	@UiConstructor
	public MusicKeyView(String labelText) {
		super(labelText);
	}

}
