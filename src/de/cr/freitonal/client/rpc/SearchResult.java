package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchResult implements IsSerializable {
	private PieceSearchMask pieceSearchMask;

	/**
	 * @param pieceSearchMask
	 *            the pieceSearchMask to set
	 */
	public void setPieceSearchMask(PieceSearchMask pieceSearchMask) {
		this.pieceSearchMask = pieceSearchMask;
	}

	/**
	 * @return the pieceSearchMask
	 */
	public PieceSearchMask getPieceSearchMask() {
		return pieceSearchMask;
	}

}
