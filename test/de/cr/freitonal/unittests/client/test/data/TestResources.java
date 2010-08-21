package de.cr.freitonal.unittests.client.test.data;

public class TestResources {
	private static final String BaseDir = "test/de/cr/freitonal/unittests/client/test/data";

	public TextResource getFullSearchJSON() {
		return new TextResource(BaseDir + "/fullSearch.json");
	}

	public TextResource getSearchForBeethovenJSON() {
		return new TextResource(BaseDir + "/searchForBeethoven.json");
	}

	public TextResource getSearchForPianoJSON() {
		return new TextResource(BaseDir + "/searchForPiano.json");
	}

	public TextResource getSearchForPianoAndViolinJSON() {
		return new TextResource(BaseDir + "/searchForPianoAndViolin.json");
	}

	public TextResource getSearchForSubtitleJSON() {
		return new TextResource(BaseDir + "/searchForEroica.json");
	}

	public TextResource getSearchForOrdinal4aJSON() {
		return new TextResource(BaseDir + "/searchForOrdinal4a.json");
	}

	public TextResource getSearchForAMajorJSON() {
		return new TextResource(BaseDir + "/searchForAMajor.json");
	}

	public TextResource getSearchForQuartettJSON() {
		return new TextResource(BaseDir + "/searchForQuartett.json");
	}
}
