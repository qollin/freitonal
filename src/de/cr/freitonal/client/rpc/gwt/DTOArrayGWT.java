package de.cr.freitonal.client.rpc.gwt;

import com.google.gwt.json.client.JSONArray;

import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOArrayGWT implements DTOArray {

	private final JSONArray jsonArray;

	public DTOArrayGWT(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public DTOValue get(int i) {
		return new DTOValueGWT(jsonArray.get(i));
	}

	public int size() {
		return jsonArray.size();
	}
}
