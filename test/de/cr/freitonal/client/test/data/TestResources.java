package de.cr.freitonal.client.test.data;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface TestResources extends ClientBundle {
	@Source("de/cr/freitonal/client/test/data/fullSearch.json")
	TextResource getFullSearchJSON();

	@Source("de/cr/freitonal/client/test/data/searchForBeethoven.json")
	TextResource getSearchForBeethovenJSON();

	@Source("de/cr/freitonal/client/test/data/searchForPiano.json")
	TextResource getSearchForPianoJSON();
}
