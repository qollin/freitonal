package de.cr.freitonal.client.rpc.gwt;

import com.google.gwt.json.client.JSONNumber;

import de.cr.freitonal.client.rpc.dto.DTONumber;

public class DTONumberGWT implements DTONumber {

	private final JSONNumber jsonNumber;

	DTONumberGWT(JSONNumber jsonNumber) {
		this.jsonNumber = jsonNumber;
	}

	public double doubleValue() {
		return jsonNumber.doubleValue();
	}

}
