package de.cr.freitonal.unittests.client.widgets.piecelist;

import java.util.ArrayList;

import de.cr.freitonal.client.widgets.piecelist.PieceListPresenter.View;
import de.cr.freitonal.shared.models.Piece;

public class PieceListViewMock implements View {
	private final ArrayList<String> trace;

	public PieceListViewMock(ArrayList<String> trace) {
		this.trace = trace;
	}

	@Override
	public void setPieceList(ArrayList<Piece> pieceList) {
		trace.add("setPieceList");
	}
}
