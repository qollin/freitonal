package de.cr.freitonal.server.services;

import java.io.File;
import java.util.HashSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import clojure.lang.RT;
import clojure.lang.Var;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ClojureServlet extends RemoteServiceServlet {
	protected static File confDir = new File("/Users/collin/Documents/workspace/freitonalGUI/conf");
	private String namespace;
	private final HashSet<String> loadedResources = new HashSet<String>();

	protected String getDatabaseConfigFile() {
		return "db.clj";
	}

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			super.init(servletConfig);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		loadNamespace();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		loadNamespace();
	}

	private void loadNamespace() {
		try {
			String file = namespace.replace('.', '/') + ".clj";
			if (!loadedResources.contains(file)) {
				RT.loadResourceScript(file);
				loadedResources.add(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setClojureNamespace(String namespace) {
		this.namespace = namespace;
	}

	protected Object runFunction(String functionName, Object parameter) {
		Var function = RT.var(namespace, functionName);
		try {
			return function.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile(), parameter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Object runFunction(String functionName) {
		Var function = RT.var(namespace, functionName);
		try {
			return function.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}