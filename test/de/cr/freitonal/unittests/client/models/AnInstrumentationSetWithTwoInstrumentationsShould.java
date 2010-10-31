package de.cr.freitonal.unittests.client.models;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;

public class AnInstrumentationSetWithTwoInstrumentationsShould {
	private final Item violin = new Item("violin", "violin");
	private final Item piano = new Item("piano", "piano");
	private ArrayList<Instrumentation> instrumentations;
	private InstrumentationSet instrumentationSet;

	@Before
	public void setupInstrumentationSet() {
		instrumentations = new ArrayList<Instrumentation>();
		ArrayList<Item> instruments = new ArrayList<Item>();
		instruments.add(violin);
		instruments.add(piano);

		ArrayList<Item> instruments2 = new ArrayList<Item>();
		instruments.add(violin);

		instrumentations.add(new Instrumentation("id", "nickname", instruments));
		instrumentations.add(new Instrumentation("id2", "nickname2", instruments2));
		instrumentationSet = new InstrumentationSet(instrumentations);
	}

	@Test
	public void ReturnTheInstrumentations() {
		assertSame(instrumentations, instrumentationSet.getInstrumentations());
	}
}
