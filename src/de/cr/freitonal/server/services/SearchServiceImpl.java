package de.cr.freitonal.server.services;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import de.cr.freitonal.client.rpc.ModelFactory;
import de.cr.freitonal.client.rpc.SearchResult;
import de.cr.freitonal.client.rpc.SearchService;
import de.cr.freitonal.shared.models.PieceList;
import de.cr.freitonal.shared.parameters.SearchParameters;
import de.cr.freitonal.unittests.client.rpc.java.DTOParserJava;

@SuppressWarnings("serial")
public class SearchServiceImpl extends ClojureServlet implements SearchService {
	private static final String SEARCH_FRONTEND_NAMESPACE = "de.cr.freitonal.server.search-frontend";
	private final ModelFactory modelFactory = new ModelFactory(new DTOParserJava());

	@Override
	public void init(ServletConfig servletConfig) {
		setClojureNamespace(SEARCH_FRONTEND_NAMESPACE);
		super.init(servletConfig);
	}

	@Override
	public void init() throws ServletException {
		setClojureNamespace(SEARCH_FRONTEND_NAMESPACE);
		super.init();
	}

	private SearchResult createSearchResult(String jsonString, PieceList pieceList) {
		SearchResult result = modelFactory.createSearchResult(jsonString);
		result.setPieceList(pieceList);

		return result;
	}

	@Override
	public SearchResult search(SearchParameters searchParameters) {
		try {
			String jsonString = (String) runFunction("doSearch", searchParameters);
			PieceList pieceList = (PieceList) runFunction("doListPieces", searchParameters);
			return createSearchResult(jsonString, pieceList);
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
