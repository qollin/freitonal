package de.cr.freitonal.client.models;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.shared.models.Item;

public class PieceTypeSet implements IsSerializable {
	private ItemSet pieceTypes;
	private ItemSet piecePlusInstrumentationTypes;
	private MultiSourceItemSet allTypes;

	private PieceTypeSet() {
	}

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
