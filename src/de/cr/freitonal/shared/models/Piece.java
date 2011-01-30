package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Piece extends VolatilePiece implements UID, IsSerializable {
	private String id;

	public Piece(String id, VolatilePiece piece) {
		super();
		setComposer(piece.getComposer());
		setInstrumentation(piece.getInstrumentation());
		setOrdinal(piece.getOrdinal());
		setSubtitle(piece.getSubtitle());
		setMusicKey(piece.getMusicKey());
		setParent(piece.getParent());
		setCatalog(piece.getCatalog());
		setPieceType(piece.getPieceType());
		setPublicationDate(piece.getPublicationDate());
		this.id = id;
	}

	@SuppressWarnings("unused")
	private Piece() {
		//needed because of GWT serialization
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
