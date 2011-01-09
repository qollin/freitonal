package de.cr.freitonal.server.services;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import clojure.lang.RT;
import clojure.lang.Var;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.cr.freitonal.client.rpc.CreateService;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileCatalog;
import de.cr.freitonal.shared.models.VolatileInstrumentation;
import de.cr.freitonal.shared.models.VolatileItem;

@SuppressWarnings("serial")
public class CreateServiceImplOnEmptyDB extends RemoteServiceServlet implements CreateService {
	private static final String NAMESPACE = "de.cr.freitonal.server.insert";
	private static File confDir = new File("/Users/collin/Documents/workspace/freitonalGUI/conf"); //TODO

	protected String getDatabaseConfigFile() {
		return "db-empty.clj";
	}

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			super.init(servletConfig);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		try {
			RT.loadResourceScript(NAMESPACE.replace('.', '/') + ".clj");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Item createComposer(VolatileItem composer) {
		return createItem(composer, "doCreateComposer");
	}

	@Override
	public Item createInstrument(VolatileItem instrument) {
		return createItem(instrument, "doCreateInstrument");
	}

	private Item createItem(VolatileItem item, String functionName) {
		Var createFunction = RT.var(NAMESPACE, functionName);
		try {
			return (Item) createFunction.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile(), item);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Instrumentation createInstrumentation(VolatileInstrumentation instrumentation) {
		Var createFunction = RT.var(NAMESPACE, "doCreateInstrumentation");
		try {
			return (Instrumentation) createFunction.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile(), instrumentation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Item createCatalogName(VolatileItem catalogName) {
		return createItem(catalogName, "doCreateCatalogName");
	}

	@Override
	public Catalog createCatalog(VolatileCatalog catalog) {
		Var createFunction = RT.var(NAMESPACE, "doCreateCatalog");
		try {
			return (Catalog) createFunction.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile(), catalog);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Item createPieceType(VolatileItem pieceType) {
		return createItem(pieceType, "doCreatePieceType");
	}

}
