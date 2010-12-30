package de.cr.freitonal.unittests.client.models.factories;

import org.junit.Before;

import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.client.rpc.dto.DTOParser;
import de.cr.freitonal.unittests.client.rpc.java.DTOParserJava;

public class FactoryTest {
	protected DTOParser parser;

	@Before
	public void setup() {
		parser = new DTOParserJava();
	}

	protected DTOObject parse(String s) {
		return parser.parse(s).isObject();
	}

}
