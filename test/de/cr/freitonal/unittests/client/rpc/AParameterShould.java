package de.cr.freitonal.unittests.client.rpc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.rpc.Parameter;

public class AParameterShould {
	@Test
	public void BeEqualToAnotherParameter() {
		Parameter a = new Parameter("key", "value");
		Parameter b = new Parameter("key", "value");
		assertEquals(a, b);
	}
}
