package de.cr.freitonal.server.services;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import de.cr.freitonal.client.rpc.PurgeService;

@SuppressWarnings("serial")
public class PurgeServiceImplOnEmptyDB extends ClojureServlet implements PurgeService {
	@Override
	public void init(ServletConfig servletConfig) {
		setClojureNamespace("de.cr.freitonal.unittests.server.testtools");
		super.init(servletConfig);
	}

	@Override
	public void init() throws ServletException {
		setClojureNamespace("de.cr.freitonal.unittests.server.testtools");
		super.init();
	}

	@Override
	public void purgeDB() {
		runFunction("doPurgeDB");
	}

	@Override
	protected String getDatabaseConfigFile() {
		return "db-empty.clj";
	}

}
