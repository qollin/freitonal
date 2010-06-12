package de.cr.freitonal.client.rpc.gwt;

import com.google.gwt.json.client.JSONObject;

import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOObjectGWT implements DTOObject {

	private final JSONObject jsonObject;

	public DTOObjectGWT(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public DTOValue get(String key) {
		return new DTOValueGWT(jsonObject.get(key));
	}

}
