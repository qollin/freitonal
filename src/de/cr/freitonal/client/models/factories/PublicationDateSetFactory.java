package de.cr.freitonal.client.models.factories;

import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class PublicationDateSetFactory extends AbstractFactory {

	public PublicationDateSet createPublicationDateSet(DTOObject jsonObject) {
		DTOArray jsonArray = jsonObject.get("piece-publication_date").isArray();
		return new PublicationDateSet(createItemListFromRPCArray(jsonArray));
	}

}
