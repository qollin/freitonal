package de.cr.freitonal.client.rpc;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

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
		for (Item instrument : searchMask.getInstrumentations().getSelectedList()) {
			addHTTPParameter("piece-instrumentations__instrument", instrument.getID());
		}
	}

	private void catalogToHTTPParameter() {
		Item name = searchMask.getCatalogs().getNames().getSelected();
		if (name != null) {
			addHTTPParameter("piece-catalog__name", name.getID());
		}

		Item number = searchMask.getCatalogs().getNumbers().getSelected();
		if (number != null) {
			addHTTPParameter("piece-catalog__number", number.getID());
		}
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

	private void itemSetToHTTPParameter(ItemSet set, String parameterName) {
		Item item = set.getSelected();
		if (item != null) {
			addHTTPParameter(parameterName, item.getID());
		}
	}

	private void addHTTPParameter(String key, String value) {
		parameters += (firstParameter ? "?" : "&") + urlEncoder.encodeParam(key) + "=" + urlEncoder.encodeParam(value);
		firstParameter = false;
	}

}
