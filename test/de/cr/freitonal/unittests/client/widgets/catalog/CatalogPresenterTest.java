package de.cr.freitonal.unittests.client.widgets.catalog;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.catalog.CatalogPresenter;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class CatalogPresenterTest extends PresenterTest {
	protected final CatalogViewMock view = new CatalogViewMock();
	protected final CatalogSet catalogs = generateCatalogSet();
	protected CatalogPresenter catalogPresenter;

	private CatalogSet generateCatalogSet() {
		ItemSet names = new ItemSet(new Item("1", "KV"), new Item("2", "Opus"));
		ItemSet numbers = new ItemSet(new Item("1", "110"), new Item("2", "42a"));

		return new CatalogSet(names, numbers);
	}

}
