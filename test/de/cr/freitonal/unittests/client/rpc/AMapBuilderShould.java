package de.cr.freitonal.unittests.client.rpc;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.rpc.MapBuilder;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.usertests.client.test.data.TestData;

public class AMapBuilderShould {

	@Test
	public void test() {
		PieceSearchMask searchMask = new PieceSearchMask();
		CatalogSet catalogSet = TestData.createCatalogSet();
		searchMask.setCatalogs(catalogSet);
		searchMask.setComposers(TestData.createComposerSet());
		searchMask.setInstrumentations(TestData.createInstrumentationSet(1));
		searchMask.setMusicKeys(TestData.createMusicKeySet());
		searchMask.setOrdinals(TestData.createOrdinalSet());
		searchMask.setPieceTypes(TestData.createPieceTypeSet());
		searchMask.setPublicationDates(TestData.createPublicationDateSet());
		searchMask.setSubtitles(TestData.createSubtitleSet());

		ItemSet names = catalogSet.getNames();
		ItemSet numbers = catalogSet.getNumbers();
		names.setSelected(names.getItem(0));

		Map<String, String> map = new MapBuilder(searchMask).getMap();
		assertEquals(names.getItem(0).getID(), map.get("piece-catalog__name"));
	}
}
