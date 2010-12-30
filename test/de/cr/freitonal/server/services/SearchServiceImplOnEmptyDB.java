package de.cr.freitonal.server.services;

@SuppressWarnings("serial")
public class SearchServiceImplOnEmptyDB extends SearchServiceImpl {
	@Override
	protected String getDatabaseConfigFile() {
		return "db-empty.clj";
	}
}
