package de.cr.freitonal.shared.models;

public class Piece extends VolatilePiece {
	private final String id;

	public Piece(String id, VolatilePiece piece) {
		super(piece.getComposer(), piece.getInstrumentation());
		this.id = id;
	}

	public String getID() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return id.equals(((Piece) other).id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
