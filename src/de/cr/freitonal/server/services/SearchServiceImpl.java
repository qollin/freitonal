package de.cr.freitonal.server.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import de.cr.freitonal.client.rpc.MapBuilder;
import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.SearchService;
import de.cr.freitonal.shared.models.PieceList;
import de.cr.freitonal.unittests.client.rpc.java.DTOParserJava;

@SuppressWarnings("serial")
public class SearchServiceImpl extends ClojureServlet implements SearchService {
	private final ModelFactory modelFactory = new ModelFactory(new DTOParserJava());

	@Override
	public void init(ServletConfig servletConfig) {
		setClojureNamespace("de.cr.freitonal.server.search");
		super.init(servletConfig);
	}

	@Override
	public void init() throws ServletException {
		setClojureNamespace("de.cr.freitonal.server.search");
		super.init();
	}

	private Map<String, ArrayList<String>> extractParameterFromPieceSearchMask(PieceSearchMask pieceSearchMask) {
		if (pieceSearchMask != null) {
			return new MapBuilder(pieceSearchMask).getMap();
		} else {
			return new HashMap<String, ArrayList<String>>();
		}
	}

	private SearchResult createSearchResult(String jsonString, PieceSearchMask pieceSearchMask, PieceList pieceList) {
		SearchResult result = modelFactory.createSearchResult(jsonString);
		result.setPieceList(pieceList);

		if (pieceSearchMask != null) {
			pieceSearchMask.copyItemSelectionTo(result.getPieceSearchMask());
		}

		return result;
	}

	@Override
	public SearchResult search(PieceSearchMask pieceSearchMask) {
		try {
			Map<String, ArrayList<String>> parameters = extractParameterFromPieceSearchMask(pieceSearchMask);
			String jsonString = (String) runFunction("doSearch", parameters);
			PieceList pieceList = (PieceList) runFunction("doListPieces", parameters);
			return createSearchResult(jsonString, pieceSearchMask, pieceList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SearchResult search() {
		return search(null);
	}

}
