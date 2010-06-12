package de.cr.freitonal.client.rpc.gwt;

import com.google.gwt.json.client.JSONValue;

import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTONumber;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.client.rpc.dto.DTOString;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOValueGWT implements DTOValue {

	private final JSONValue jsonValue;

	public DTOValueGWT(JSONValue jsonValue) {
		this.jsonValue = jsonValue;
	}

	public DTOArray isArray() {
		return jsonValue.isArray() != null ? new DTOArrayGWT(jsonValue.isArray()) : null;
	}

	public DTOObject isObject() {
		return jsonValue.isObject() != null ? new DTOObjectGWT(jsonValue.isObject()) : null;
	}

	public DTONumber isNumber() {
		return jsonValue.isNumber() != null ? new DTONumberGWT(jsonValue.isNumber()) : null;
	}

	public DTOString isString() {
		return jsonValue.isString() != null ? new DTOStringGWT(jsonValue.isString()) : null;
	}
}
