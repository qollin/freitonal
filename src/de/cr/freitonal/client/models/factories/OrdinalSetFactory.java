package de.cr.freitonal.client.models.factories;

import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class OrdinalSetFactory extends AbstractFactory {

	public OrdinalSet createOrdinalSet(DTOObject jsonObject) {
		DTOArray jsonArray = jsonObject.get("piece-type_ordinal").isArray();
		return new OrdinalSet(createItemListFromRPCArray(jsonArray));
	}

}
