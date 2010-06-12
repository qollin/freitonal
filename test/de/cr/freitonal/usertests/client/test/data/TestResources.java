package de.cr.freitonal.usertests.client.test.data;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface TestResources extends ClientBundle {
	@Source("de/cr/freitonal/usertests/client/test/data/fullSearch.json")
	TextResource getFullSearchJSON();

	@Source("de/cr/freitonal/usertests/client/test/data/searchForBeethoven.json")
	TextResource getSearchForBeethovenJSON();

	@Source("de/cr/freitonal/usertests/client/test/data/searchForPiano.json")
	TextResource getSearchForPianoJSON();

	@Source("de/cr/freitonal/usertests/client/test/data/searchForPianoAndViolin.json")
	TextResource getSearchForPianoAndViolinJSON();

	@Source("de/cr/freitonal/usertests/client/test/data/searchForEroica.json")
	TextResource getSearchForSubtitleJSON();

	@Source("de/cr/freitonal/usertests/client/test/data/searchForOrdinal4a.json")
	TextResource getSearchForOrdinal4aJSON();

	@Source("de/cr/freitonal/usertests/client/test/data/searchForAMajor.json")
	TextResource getSearchForAMajorJSON();
}
