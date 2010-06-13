package de.cr.freitonal.client.rpc.java;

import com.google.gson.JsonElement;

import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTONumber;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.client.rpc.dto.DTOString;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOValueJava implements DTOValue {
	private final JsonElement jsonElement;

	public DTOValueJava(JsonElement jsonElement) {
		this.jsonElement = jsonElement;
	}

	public DTOArray isArray() {
		if (jsonElement.isJsonArray()) {
			return new DTOArrayJava(jsonElement.getAsJsonArray());
		}
		return null;
	}

	public DTONumber isNumber() {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return new DTONumberJava(jsonElement.getAsJsonPrimitive());
		}
		return null;
	}

	public DTOObject isObject() {
		if (jsonElement.isJsonObject()) {
			return new DTOObjectJava(jsonElement.getAsJsonObject());
		}
		return null;
	}

	public DTOString isString() {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
			return new DTOObjectString(jsonElement.getAsJsonPrimitive());
		}
		return null;
	}

}
