package de.cr.freitonal.client.models.factories;

import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class ComposerSetFactory extends AbstractFactory {

	public ComposerSet createComposerSet(DTOObject jsonObject) {
		DTOArray array = jsonObject.get("piece-composer").isArray();
		if (array == null) {
			throw new IllegalArgumentException(jsonObject.get("piece-composer") + " is not an array");
		}
		return new ComposerSet(createItemListFromRPCArray(array));
	}
}
