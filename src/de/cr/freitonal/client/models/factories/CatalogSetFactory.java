package de.cr.freitonal.client.models.factories;


import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class CatalogSetFactory extends AbstractFactory {

	public CatalogSet createCatalogSet(DTOObject jsonObject) {
		DTOArray nameArray = jsonObject.get("piece-catalog__name").isArray();
		DTOArray numberArray = jsonObject.get("piece-catalog__number").isArray();
		return new CatalogSet(createItemListFromRPCArray(nameArray), createItemListFromRPCArray(numberArray));
	}
}
