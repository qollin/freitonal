package de.cr.freitonal.unittests.client.rpc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.rpc.MapBuilder;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.usertests.client.test.data.TestData;

public class AMapBuilderShould {

	private PieceSearchMask searchMask;

	@Before
	public void setupPieceSearchMask() {
		searchMask = new PieceSearchMask();
		CatalogSet catalogSet = TestData.createCatalogSet();
		searchMask.setCatalogs(catalogSet);
		searchMask.setComposers(TestData.createComposerSet());
		searchMask.setInstrumentations(TestData.createInstrumentationSet(1));
		searchMask.setMusicKeys(TestData.createMusicKeySet());
		searchMask.setOrdinals(TestData.createOrdinalSet());
		searchMask.setPieceTypes(TestData.createPieceTypeSet());
		searchMask.setPublicationDates(TestData.createPublicationDateSet());
		searchMask.setSubtitles(TestData.createSubtitleSet());
	}

	@Test
	public void UseSelectedCatalogNamesAsSearchParameters() {
		ItemSet names = searchMask.getCatalogs().getNames();
		names.setSelected(names.getItem(0));

		Map<String, ArrayList<String>> map = new MapBuilder(searchMask).getMap();
		assertEquals(names.getItem(0).getID(), map.get("piece-catalog__name").get(0));
	}

	@Test
	public void UseSelectedPieceTypesAsSearchParameters() {
		PieceTypeSet pieceTypes = searchMask.getPieceTypes();
		pieceTypes.getAllTypesItemSet().setSelected(pieceTypes.getAllTypesItemSet().getItem(0));

		Map<String, ArrayList<String>> map = new MapBuilder(searchMask).getMap();
		assertEquals(pieceTypes.getAllTypesItemSet().getItem(0).getID(), map.get("piece-piece_type").get(0));
	}

}
