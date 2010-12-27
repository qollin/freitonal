package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.factories.InstrumentationSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;

public class AnInstrumentationSetFactoryShould extends FactoryTest {
	@Test
	public void ReturnACorrectInstrumentationSet() {
		DTOObject jsonObject = parse("{'piece-instrumentations': [{'id': 1, 'nickname': 'violin sonata', 'instruments': [['1', 'piano'], ['2', 'violin']]}]}");
		InstrumentationSetFactory factory = new InstrumentationSetFactory();
		InstrumentationSet instrumentationSet = factory.createInstrumentationSet(jsonObject);

		Instrumentation instrumentation = instrumentationSet.getInstrumentations().get(0);
		assertEquals("1", instrumentation.getID());
		assertEquals("violin sonata", instrumentation.getNickname());
		assertEquals(new Item("1", "piano"), instrumentation.getInstruments().get(0));
		assertEquals(new Item("2", "violin"), instrumentation.getInstruments().get(1));
	}

	@Test
	public void ReturnACorrectInstrumentationSetWhenInstrumentCountsAreGiven() {
		DTOObject jsonObject = parse("{'piece-instrumentations': [{'id': 1, 'nickname': 'two pianos', 'instruments': [['1', 'piano', 2]]}]}");
		InstrumentationSetFactory factory = new InstrumentationSetFactory();
		InstrumentationSet instrumentationSet = factory.createInstrumentationSet(jsonObject);

		Instrumentation instrumentation = instrumentationSet.getInstrumentations().get(0);
		assertEquals("1", instrumentation.getID());
		assertEquals("two pianos", instrumentation.getNickname());
		Item piano = instrumentation.getInstruments().get(0);
		assertEquals(new Item("1", "piano"), piano);
		assertEquals(2, instrumentation.getInstrumentCount(piano));
	}
}
