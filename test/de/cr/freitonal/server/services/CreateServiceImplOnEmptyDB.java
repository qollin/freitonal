package de.cr.freitonal.server.services;

@SuppressWarnings("serial")
public class CreateServiceImplOnEmptyDB extends CreateServiceImpl {
	@Override
	protected String getDatabaseConfigFile() {
		return "db-empty.clj";
	}
}
