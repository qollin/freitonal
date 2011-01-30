package de.cr.freitonal.client.models;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class InstrumentationSet implements IsSerializable, Set {
	private ArrayList<Instrumentation> instrumentations;
	private VolatileInstrumentation searchPattern;

	@SuppressWarnings("unused")
	private InstrumentationSet() {
		//needed because of GWT serialization
	}

	public InstrumentationSet(ArrayList<Instrumentation> instrumentations) {
		this.instrumentations = instrumentations;
	}

	public InstrumentationSet(Instrumentation... instrumentations) {
		this.instrumentations = new ArrayList<Instrumentation>(Arrays.asList(instrumentations));
	}

	public ArrayList<Instrumentation> getInstrumentations() {
		return instrumentations;
	}

	public void setSearchPattern(VolatileInstrumentation searchPattern) {
		this.searchPattern = searchPattern;

	}

	public VolatileInstrumentation getSearchPattern() {
		return searchPattern;
	}

	public int size() {
		return instrumentations.size();
	}

	public boolean contains(Object o) {
		Instrumentation instrumentation = (Instrumentation) o;
		return instrumentations.contains(instrumentation);
	}
}
