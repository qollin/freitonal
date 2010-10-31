package de.cr.freitonal.client.models.factories;

import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;

public class MusicKeySetFactory extends AbstractFactory {

	public MusicKeySet createMusicKeySet(DTOObject jsonObject) {
		DTOArray jsonArray = jsonObject.get("piece-music_key").isArray();

		return new MusicKeySet(createItemListFromRPCArray(jsonArray));
	}

}
