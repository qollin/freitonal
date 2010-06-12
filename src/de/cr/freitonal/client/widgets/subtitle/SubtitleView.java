package de.cr.freitonal.client.widgets.subtitle;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.BaseView;

public class SubtitleView extends BaseView implements SubtitlePresenter.View {

	@UiConstructor
	public SubtitleView(String labelText) {
		super(labelText);
	}
}
