package de.cr.freitonal.client.rpc;

import java.util.ArrayList;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.rpc.factories.CatalogHTTPParameterFactory;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileInstrumentation;

public class JSONFactory {
	private final PieceSearchMask searchMask;
	private String parameters = "";
	private boolean firstParameter = true;
	private final URLEncoder urlEncoder;

	public JSONFactory(PieceSearchMask searchMask, URLEncoder urlEncoder) {
		this.searchMask = searchMask;
		this.urlEncoder = urlEncoder;

		composerToHTTPParameter();
		catalogToHTTPParameter();
		instrumentationsToHTTPParameter();
		subtitleToHTTPParameter();
		ordinalToHTTPParameter();
		musicKeyToHTTPParameter();
	}

	public String getHTTPParameters() {
		return parameters;
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
		CatalogHTTPParameterFactory factory = new CatalogHTTPParameterFactory(urlEncoder);
		ArrayList<String> httpParams = factory.createHTTPParameters(searchMask.getCatalogs());
		addHTTPParameters(httpParams);

	}

	private void composerToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getComposers(), "piece-composer");
	}

	private void subtitleToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getSubtitles(), "piece-subtitle");
	}

	private void ordinalToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getOrdinals(), "piece-type_ordinal");
	}

	private void musicKeyToHTTPParameter() {
		itemSetToHTTPParameter(searchMask.getMusicKeys(), "piece-music_key");
	}

	private void itemSetToHTTPParameter(ItemSet itemSet, String parameterName) {
		Item item = itemSet.getSelected();
		if (item != null) {
			addHTTPParameter(parameterName, item.getID());
		}
	}

	private void addHTTPParameters(ArrayList<String> httpParameters) {
		for (String httpParameter : httpParameters) {
			addHTTPParameter(httpParameter);
		}
	}

	private void addHTTPParameter(String key, String value) {
		addHTTPParameter(urlEncoder.encodeParam(key) + "=" + urlEncoder.encodeParam(value));
	}

	private void addHTTPParameter(String httpParameter) {
		parameters += (firstParameter ? "?" : "&") + httpParameter;
		firstParameter = false;
	}

}
