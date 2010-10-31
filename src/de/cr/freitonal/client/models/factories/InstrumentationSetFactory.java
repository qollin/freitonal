package de.cr.freitonal.client.models.factories;

import java.util.ArrayList;

import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.rpc.dto.DTOArray;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;

public class InstrumentationSetFactory extends AbstractFactory {
	private Instrumentation createInstrumentation(DTOObject rawInstrumentation) {
		ArrayList<Item> instruments = createItemListFromRPCArray(rawInstrumentation.get("instruments").isArray());
		Instrumentation instrumentation = new Instrumentation(forceString(rawInstrumentation.get("id")),
				forceString(rawInstrumentation.get("nickname")), instruments);

		return instrumentation;
	}

	public InstrumentationSet createInstrumentationSet(DTOObject jsonObject) {
		DTOArray jsonArray = jsonObject.get("piece-instrumentations").isArray();
		ArrayList<Instrumentation> instrumentations = new ArrayList<Instrumentation>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Instrumentation instrumentation = createInstrumentation(jsonArray.get(i).isObject());
			instrumentations.add(instrumentation);
		}
		InstrumentationSet instrumentationSet = new InstrumentationSet(instrumentations);

		return instrumentationSet;
	}

}
