package de.cr.freitonal.shared.renderer;

import de.cr.freitonal.shared.models.Piece;

public class PieceRenderer {
	private final InstrumentationRenderer instrumentationRenderer = new InstrumentationRenderer();
	private static final String FOR = " für ";
	private String title;
	private Piece piece;

	public String render(Piece piece) {
		this.piece = piece;
		title = "";

		renderComposer();
		renderPieceType();
		renderInstrumentation();

		return title;
	}

	private void renderInstrumentation() {
		if (piece.getInstrumentation() != null) {
			title += FOR + instrumentationRenderer.render(piece.getInstrumentationAsNonVolatile());
		}
	}

	private void renderPieceType() {
		if (piece.getPieceType() != null) {
			String pieceType = piece.getPieceType().getValue();
			title += " - " + pieceType;
		}
	}

	private void renderComposer() {
		if (piece.getComposer() != null) {
			String composer = piece.getComposer().getValue();
			title += composer;
		}
	}
}
