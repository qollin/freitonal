package de.cr.freitonal.client.rpc.gwt;

import com.google.gwt.json.client.JSONParser;

import de.cr.freitonal.client.rpc.dto.DTOParser;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOParserGWT implements DTOParser {

	public DTOValue parse(String jsonString) {
		return new DTOValueGWT(JSONParser.parseStrict(jsonString));
	}
}
