package de.cr.freitonal.unittests.client.models;

import static de.cr.freitonal.unittests.client.test.data.TestData.Oboe;
import static de.cr.freitonal.unittests.client.test.data.TestData.Piano;
import static de.cr.freitonal.unittests.client.test.data.TestData.Violin;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.shared.models.Instrumentation;

public class AnInstrumentationShould {
	@Test
	public void RenderTheNicknameWhenOneIsGiven() {
		Instrumentation instrumentation = new Instrumentation("1", "solo piano", Piano);
		assertEquals("solo piano", instrumentation.toString());
	}

	@Test
	public void RenderTheListOfInstrumentsWhenAnEmptyNicknameIsGiven() {
		Instrumentation instrumentation = new Instrumentation("1", "", Piano);
		assertEquals(Piano.getValue(), instrumentation.toString());
	}

	@Test
	public void RenderTheListOfInstrumentsWhenNoNicknameIsGiven() {
		Instrumentation instrumentation = new Instrumentation("1", null, Piano);
		assertEquals(Piano.getValue(), instrumentation.toString());
	}

	@Test
	public void RenderTheListOfInstrumentsWithTwoInstruments() {
		Instrumentation instrumentation = new Instrumentation("1", null, Piano, Violin);
		assertEquals(Piano.getValue() + " und " + Violin.getValue(), instrumentation.toString());
	}

	@Test
	public void RenderTheListOfInstrumentsWithThreeInstruments() {
		Instrumentation instrumentation = new Instrumentation("1", null, Piano, Violin, Oboe);
		assertEquals(Piano.getValue() + ", " + Violin.getValue() + " und " + Oboe.getValue(), instrumentation.toString());
	}

}
