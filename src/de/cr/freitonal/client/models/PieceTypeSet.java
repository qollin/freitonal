package de.cr.freitonal.client.models;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.shared.models.Item;

public class PieceTypeSet implements IsSerializable, Set {
	private ItemSet pieceTypes;
	private ItemSet piecePlusInstrumentationTypes;
	private MultiSourceItemSet allTypes;

	@SuppressWarnings("unused")
	private PieceTypeSet() {
		//needed because of GWT serialization
	}

	public PieceTypeSet(ItemSet pieceTypes, ItemSet piecePlusInstrumentationTypes) {
		this.pieceTypes = pieceTypes;
		this.piecePlusInstrumentationTypes = piecePlusInstrumentationTypes;
		this.allTypes = new MultiSourceItemSet(this.piecePlusInstrumentationTypes, this.pieceTypes);
	}

	public PieceTypeSet(ArrayList<Item> pieceTypes, ArrayList<Item> piecePlusInstrumentationTypes) {
		this(new ItemSet(pieceTypes), new ItemSet(piecePlusInstrumentationTypes));
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

	@Override
	public boolean contains(Object o) {
		Item item = (Item) o;
		return pieceTypes.contains(item) || piecePlusInstrumentationTypes.contains(item);
	}
}
