package de.cr.freitonal.client.rpc.java;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOObjectJava implements DTOObject {

	private final JsonObject jsonObject;

	public DTOObjectJava(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public DTOValue get(String key) {
		JsonElement value = jsonObject.get(key);
		if (value == null) {
			return null;
		}
		return new DTOValueJava(value);
	}
}
