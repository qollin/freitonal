package de.cr.freitonal.client.rpc.gwt;

import com.google.gwt.json.client.JSONString;

import de.cr.freitonal.client.rpc.dto.DTOString;

public class DTOStringGWT implements DTOString {

	private final JSONString jsonString;

	public DTOStringGWT(JSONString jsonString) {
		this.jsonString = jsonString;
	}

	public String stringValue() {
		return jsonString.stringValue();
	}

}
