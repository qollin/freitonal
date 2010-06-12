package de.cr.freitonal.client.rpc;

import com.google.gwt.http.client.URL;

import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.models.ItemSet;

public class JSONFactory {
	private final PieceSearchMask searchMask;
	private String parameters = "";
	private boolean firstParameter = true;

	public JSONFactory(PieceSearchMask searchMask) {
		this.searchMask = searchMask;

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
			addHTTPParameter("piece-instrumentations__instrument", instrument.id);
		}
	}

	private void catalogToHTTPParameter() {
		Item name = searchMask.getCatalogs().getNames().getSelected();
		if (name != null) {
			addHTTPParameter("piece-catalog__name", name.id);
		}

		Item number = searchMask.getCatalogs().getNumbers().getSelected();
		if (number != null) {
			addHTTPParameter("piece-catalog__number", number.id);
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
			addHTTPParameter(parameterName, item.id);
		}
	}

	private void addHTTPParameter(String key, String value) {
		parameters += (firstParameter ? "?" : "&") + URL.encode(key) + "=" + URL.encode(value);
		firstParameter = false;
	}

}
