package de.cr.freitonal.client.rpc;

import com.google.gwt.http.client.URL;

import de.cr.freitonal.client.models.Item;

public class JSONFactory {
	private PieceSearchMask searchMask;
	private String parameters = "";

	public String toHTTPParameters(PieceSearchMask searchMask) {
		this.searchMask = searchMask;
		composerToHTTPParameter();
		catalogToHTTPParameter();

		return parameters.toString();
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
		Item composer = searchMask.getComposers().getSelected();
		if (composer != null) {
			addHTTPParameter("piece-composer", composer.id);
		}
	}

	private void addHTTPParameter(String key, String value) {
		parameters += "?" + URL.encode(key) + "=" + URL.encode(value);
	}

}
