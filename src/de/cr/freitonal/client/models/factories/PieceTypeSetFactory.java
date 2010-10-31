package de.cr.freitonal.client.models.factories;

import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class PieceTypeSetFactory extends AbstractFactory {

	public PieceTypeSet createPieceTypeSet(DTOObject jsonObject) {
		DTOArray pieceTypes = jsonObject.get("piece-piece_type").isArray();
		DTOArray piecePlusInstrumentationTypes = jsonObject.get("piece-type+instrumentation").isArray();
		return new PieceTypeSet(createItemListFromRPCArray(pieceTypes), createItemListFromRPCArray(piecePlusInstrumentationTypes));
	}

}
