package de.cr.freitonal.shared.renderer;

import de.cr.freitonal.shared.models.Piece;

public class PieceRenderer {

	public String render(Piece piece) {
		String title = "";

		if (piece.getComposer() != null) {
			String composer = piece.getComposer().getValue();
			title += composer;
		}
		if (piece.getPieceType() != null) {
			String pieceType = piece.getPieceType().getValue();
			title += " - " + pieceType;
		}

		return title;
	}

}
