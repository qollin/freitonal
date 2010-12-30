package de.cr.freitonal.server.services;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import clojure.lang.RT;
import clojure.lang.Var;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.cr.freitonal.client.rpc.CreateService;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileItem;

@SuppressWarnings("serial")
public class CreateServiceImplOnEmptyDB extends RemoteServiceServlet implements CreateService {
	private static File confDir = new File("/Users/collin/Documents/workspace/freitonalGUI/conf"); //TODO

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			super.init(servletConfig);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		try {
			RT.loadResourceScript("de/cr/freitonal/server/insert.clj");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Item createComposer(VolatileItem composer) {
		Var createComposerFunction = RT.var("de.cr.freitonal.server.insert", "doCreateComposer");
		try {
			return (Item) createComposerFunction.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile(), composer);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String getDatabaseConfigFile() {
		return "db-empty.clj";
	}

}
