package de.cr.freitonal.client.rpc.factories;

import java.util.ArrayList;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.rpc.Parameter;
import de.cr.freitonal.shared.models.Item;

public class CatalogHTTPParameterFactory {

	private Parameter addHTTPParameter(String key, String value) {
		return new Parameter(key, value);
	}

	public ArrayList<Parameter> createHTTPParameters(CatalogSet catalogSet) {
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
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
