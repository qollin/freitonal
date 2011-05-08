package de.cr.freitonal.client.widgets.piecelist;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.renderer.PieceRenderer;

public class PieceListView extends Composite implements PieceListPresenter.View {
	interface Binder extends UiBinder<HTMLPanel, PieceListView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private final PieceRenderer renderer = new PieceRenderer();
	private final ListDataProvider<String> dataProvider = new ListDataProvider<String>();

	@UiField
	SimplePager pager;

	@UiField
	HTMLPanel cellListPanel;

	private CellList<String> cellList;

	@UiConstructor
	public PieceListView() {
		HTMLPanel panel = binder.createAndBindUi(this);

		initCellList();
		initWidget(panel);
	}

	private void initCellList() {
		cellList = new CellList<String>(new TextCell());
		cellList.setPageSize(10);
		dataProvider.addDataDisplay(cellList);
		cellListPanel.add(cellList);
		pager.setDisplay(cellList);
	}

	private void renderTo(List<String> result, ArrayList<Piece> pieceList) {
		for (Piece piece : pieceList) {
			result.add(renderer.render(piece));
		}
	}

	@Override
	public void setPieceList(ArrayList<Piece> pieceList) {
		dataProvider.getList().clear();
		renderTo(dataProvider.getList(), pieceList);
	}

}
