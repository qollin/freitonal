package de.cr.freitonal.client.models;

import java.util.ArrayList;

import de.cr.freitonal.shared.models.Item;

public class PieceTypeSet {
	private final ItemSet pieceTypes;
	private final ItemSet piecePlusInstrumentationTypes;
	private final MultiSourceItemSet allTypes;

	public PieceTypeSet(ArrayList<Item> pieceTypes, ArrayList<Item> piecePlusInstrumentationTypes) {
		this.pieceTypes = new ItemSet(pieceTypes);
		this.piecePlusInstrumentationTypes = new ItemSet(piecePlusInstrumentationTypes);
		this.allTypes = new MultiSourceItemSet(this.piecePlusInstrumentationTypes, this.pieceTypes);
	}

	/**
	 * @return the pieceTypes
	 */
	public ItemSet getPieceTypes() {
		return pieceTypes;
	}

	/**
	 * @return the piecePlusInstrumentationTypes
	 */
	public ItemSet getPiecePlusInstrumentationTypes() {
		return piecePlusInstrumentationTypes;
	}

	public void copyItemSelectionTo(PieceTypeSet target) {
		allTypes.copyItemSelectionTo(target.allTypes);
	}

	public int size() {
		return piecePlusInstrumentationTypes.size() + pieceTypes.size();
	}

	public ItemSet getAllTypesItemSet() {
		return allTypes;
	}

}
