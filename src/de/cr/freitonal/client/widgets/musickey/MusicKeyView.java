package de.cr.freitonal.client.widgets.musickey;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.BaseView;

public class MusicKeyView extends BaseView implements MusicKeyPresenter.View {

	@UiConstructor
	public MusicKeyView(String labelText) {
		super(labelText);
	}

}
