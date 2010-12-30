package de.cr.freitonal.unittests.client.rpc.java;

import com.google.gson.JsonPrimitive;

import de.cr.freitonal.client.rpc.dto.DTOString;

public class DTOObjectString implements DTOString {

	private final JsonPrimitive jsonPrimitive;

	public DTOObjectString(JsonPrimitive jsonPrimitive) {
		this.jsonPrimitive = jsonPrimitive;
	}

	public String stringValue() {
		return jsonPrimitive.getAsString();
	}

}
