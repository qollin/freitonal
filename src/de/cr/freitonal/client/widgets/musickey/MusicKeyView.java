package de.cr.freitonal.client.widgets.musickey;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.SimpleView;

public class MusicKeyView extends SimpleView implements MusicKeyPresenter.View {

	@UiConstructor
	public MusicKeyView(String labelText) {
		super(labelText);
	}

}
