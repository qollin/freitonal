package de.cr.freitonal.client.widgets.piecelist;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;

import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.renderer.PieceRenderer;

public class PieceListView extends CellTable<Piece> implements PieceListPresenter.View {

	public PieceListView() {
		super(10);
		final PieceRenderer renderer = new PieceRenderer();
		TextColumn<Piece> idColumn = new TextColumn<Piece>() {
			@Override
			public String getValue(Piece piece) {
				return renderer.render(piece);
			}
		};
		addColumn(idColumn, "Piece");
	}

	@Override
	public void setPieceList(ArrayList<Piece> pieceList) {
		setRowData(0, pieceList);
	}

}
