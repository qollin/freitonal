package de.cr.freitonal.client.rpc.factories;

import java.util.ArrayList;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.rpc.URLEncoder;
import de.cr.freitonal.shared.models.Item;

public class CatalogHTTPParameterFactory {
	private final URLEncoder urlEncoder;

	public CatalogHTTPParameterFactory(URLEncoder urlEncoder) {
		this.urlEncoder = urlEncoder;
	}

	private String addHTTPParameter(String key, String value) {
		return urlEncoder.encodeParam(key) + "=" + urlEncoder.encodeParam(value);
	}

	public ArrayList<String> createHTTPParameters(CatalogSet catalogSet) {
		ArrayList<String> parameters = new ArrayList<String>();
		Item number = catalogSet.getNumbers().getSelected();
		if (number != null) {
			parameters.add(addHTTPParameter("piece-catalog", number.getID()));
		} else {
			Item name = catalogSet.getNames().getSelected();
			if (name != null) {
				parameters.add(addHTTPParameter("piece-catalog__name", name.getID()));
			}
		}

		return parameters;
	}
}
