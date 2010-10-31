package de.cr.freitonal.unittests.client.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class AnInstrumentationSetShould {
	private InstrumentationSet instrumentationSet;

	@Before
	public void setupInstrumentationSet() {
		ArrayList<Instrumentation> instrumentations = new ArrayList<Instrumentation>();
		instrumentationSet = new InstrumentationSet(instrumentations);
	}

	@Test
	public void BeAbleToReturnASearchPattern() {
		ArrayList<Item> instruments = new ArrayList<Item>();
		instruments.add(new Item("1", "piano"));
		VolatileInstrumentation searchPattern = new VolatileInstrumentation("nickname", instruments);
		instrumentationSet.setSearchPattern(searchPattern);

		assertEquals(searchPattern, instrumentationSet.getSearchPattern());
	}
}
