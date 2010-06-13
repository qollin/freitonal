package de.cr.freitonal.client.rpc.java;

import com.google.gson.JsonParser;

import de.cr.freitonal.client.rpc.dto.DTOParser;
import de.cr.freitonal.client.rpc.dto.DTOValue;

public class DTOParserJava implements DTOParser {
	private final JsonParser parser = new JsonParser();

	public DTOValue parse(String jsonString) {
		return new DTOValueJava(parser.parse(jsonString));
	}

}
