package de.cr.freitonal.client.models.factories;

import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class SubtitleSetFactory extends AbstractFactory {

	public SubtitleSet createSubtitleSet(DTOObject jsonObject) {
		DTOArray jsonArray = jsonObject.get("piece-subtitle").isArray();
		return new SubtitleSet(createItemListFromRPCArray(jsonArray));
	}
}
