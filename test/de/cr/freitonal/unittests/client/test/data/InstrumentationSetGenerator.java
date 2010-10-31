package de.cr.freitonal.unittests.client.test.data;

import java.util.ArrayList;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;

public class InstrumentationSetGenerator {
	private static final Item violin = new Item("violin", "violin");
	private static final Item piano = new Item("piano", "piano");

	public static InstrumentationSet generateInstrumentationSet() {
		return generateInstrumentationSet(2);
	}

	public static InstrumentationSet generateInstrumentationSet(int numberOfInstrumentations) {
		ArrayList<Instrumentation> instrumentations = new ArrayList<Instrumentation>();

		for (int i = 1; i < numberOfInstrumentations; i++) {
			ArrayList<Item> instruments = new ArrayList<Item>();
			instruments.add(violin);
			instruments.add(piano);

			instrumentations.add(new Instrumentation("id" + i, "nickname" + i, instruments));
		}
		return new InstrumentationSet(instrumentations);

	}
}
