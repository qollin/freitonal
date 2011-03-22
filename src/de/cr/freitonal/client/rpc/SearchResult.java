package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.shared.models.PieceList;

public class SearchResult implements IsSerializable {
	private PieceSearchMask pieceSearchMask;
	private PieceList pieceList;

	/**
	 * @param pieceSearchMask
	 *            the pieceSearchMask to set
	 */
	public void setPieceSearchMask(PieceSearchMask pieceSearchMask) {
		this.pieceSearchMask = pieceSearchMask;
	}

	public void setPieceList(PieceList pieceList) {
		this.pieceList = pieceList;
	}

	/**
	 * @return the pieceSearchMask
	 */
	public PieceSearchMask getPieceSearchMask() {
		return pieceSearchMask;
	}

	public PieceList getPieceList() {
		return pieceList;
	}

}
