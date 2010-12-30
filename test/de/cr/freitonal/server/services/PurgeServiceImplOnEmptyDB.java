package de.cr.freitonal.server.services;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import clojure.lang.RT;
import clojure.lang.Var;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.cr.freitonal.client.rpc.PurgeService;

@SuppressWarnings("serial")
public class PurgeServiceImplOnEmptyDB extends RemoteServiceServlet implements PurgeService {
	private static File confDir = new File("/Users/collin/Documents/workspace/freitonalGUI/conf"); //TODO

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			super.init(servletConfig);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		try {
			RT.loadResourceScript("de/cr/freitonal/unittests/server/testtools.clj");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void purgeDB() {
		Var purgeFunction = RT.var("de.cr.freitonal.unittests.server.testtools", "doPurgeDB");
		try {
			purgeFunction.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getDatabaseConfigFile() {
		return "db-empty.clj";
	}

}
