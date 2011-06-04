package de.cr.freitonal.client.rpc;

import java.util.ArrayList;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.rpc.factories.CatalogHTTPParameterFactory;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public abstract class AbstractBuilder {
	private final PieceSearchMask searchMask;

	public AbstractBuilder(PieceSearchMask searchMask) {
		this.searchMask = searchMask;
	}

	protected void addParameters() {
		composerToHTTPParameter();
		catalogToHTTPParameter();
		instrumentationsToHTTPParameter();
		subtitleToHTTPParameter();
		publicationDateToHTTPParameter();
		ordinalToHTTPParameter();
		musicKeyToHTTPParameter();
		pieceTypeToHTTPParameter();
	}

	private void instrumentationsToHTTPParameter() {
		VolatileInstrumentation searchPattern = searchMask.getInstrumentations().getSearchPattern();
		if (searchPattern != null) {
			for (Item instrument : searchPattern.getInstruments()) {
				addHTTPParameter("piece-instrumentations__instrument", instrument.getID());
			}
		}
	}

	private void catalogToHTTPParameter() {
		CatalogHTTPParameterFactory factory = new CatalogHTTPParameterFactory();
		ArrayList<Parameter> httpParams = factory.createHTTPParameters(searchMask.getCatalogs());
		addHTTPParameters(httpParams);
	}

	private void composerToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getComposers(), "piece-composer");
	}

	private void subtitleToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getSubtitles(), "piece-subtitle");
	}

	private void publicationDateToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getPublicationDates(), "piece-publication_date");
	}

	private void ordinalToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getOrdinals(), "piece-type_ordinal");
	}

	private void musicKeyToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getMusicKeys(), "piece-music_key");
	}

	private void pieceTypeToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getPieceTypes().getAllTypesItemSet(), "piece-piece_type");
	}

	private void itemSetToHTTPParameter(ItemSet itemSet, String parameterName) {
		Item item = itemSet.getSelected();
		if (item != null && !item.getID().equals("")) { //hack! Somehow item != Item.NULL_ITEM didn't work... GWT issue?
			addHTTPParameter(parameterName, item.getID());
		}
	}

	private void addHTTPParameters(ArrayList<Parameter> parameters) {
		for (Parameter parameter : parameters) {
			addHTTPParameter(parameter.getKey(), parameter.getValue());
		}
	}

	protected abstract void addHTTPParameter(String key, String value);

}
