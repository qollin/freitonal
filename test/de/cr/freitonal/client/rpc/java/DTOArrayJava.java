package de.cr.freitonal.client.rpc.java;

import com.google.gson.JsonArray;

import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOArrayJava implements DTOArray {

	private final JsonArray jsonArray;

	public DTOArrayJava(JsonArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public DTOValue get(int i) {
		return new DTOValueJava(jsonArray.get(i));
	}

	public int size() {
		return jsonArray.size();
	}

}
