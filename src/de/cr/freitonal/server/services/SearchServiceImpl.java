package de.cr.freitonal.server.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import clojure.lang.RT;
import clojure.lang.Var;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.cr.freitonal.client.rpc.MapBuilder;
import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.SearchService;
import de.cr.freitonal.unittests.client.rpc.java.DTOParserJava;

@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {
	private static File confDir = new File("/Users/collin/Documents/workspace/freitonalGUI/conf"); //TODO

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			super.init(servletConfig);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		try {
			RT.loadResourceScript("de/cr/freitonal/server/search.clj");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SearchResult search(PieceSearchMask pieceSearchMask) {
		Var searchFunction = RT.var("de.cr.freitonal.server.search", "doSearch");
		try {
			Map<String, ArrayList<String>> parameters;
			if (pieceSearchMask != null) {
				parameters = new MapBuilder(pieceSearchMask).getMap();
			} else {
				parameters = new HashMap<String, ArrayList<String>>();
			}
			String jsonString = (String) searchFunction.invoke(confDir.getAbsolutePath() + "/" + getDatabaseConfigFile(), parameters);

			ModelFactory modelFactory = new ModelFactory(new DTOParserJava());
			SearchResult result = modelFactory.createSearchResult(jsonString);

			if (pieceSearchMask != null) {
				pieceSearchMask.copyItemSelectionTo(result.getPieceSearchMask());
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String getDatabaseConfigFile() {
		return "db.clj";
	}

}
