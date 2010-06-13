package de.cr.freitonal.client.rpc.java;

import com.google.gson.JsonPrimitive;

import de.cr.freitonal.client.rpc.dto.DTONumber;

public class DTONumberJava implements DTONumber {

	private final JsonPrimitive jsonPrimitive;

	public DTONumberJava(JsonPrimitive jsonPrimitive) {
		this.jsonPrimitive = jsonPrimitive;
	}

	public double doubleValue() {
		return jsonPrimitive.getAsDouble();
	}

}
