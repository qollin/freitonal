package de.cr.freitonal.client.widgets.piecetype;

import com.google.gwt.uibinder.client.UiConstructor;

import de.cr.freitonal.client.widgets.base.BaseView;

public class PieceTypeView extends BaseView implements PieceTypePresenter.View {

	@UiConstructor
	public PieceTypeView(String labelText) {
		super(labelText);
	}

}
