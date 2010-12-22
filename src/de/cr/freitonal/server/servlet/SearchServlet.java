package de.cr.freitonal.server.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clojure.lang.RT;
import clojure.lang.Var;

public class SearchServlet extends HttpServlet {
	private static File confDir = new File("../conf");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig servletConfig) {
		try {
			RT.loadResourceScript("de/cr/freitonal/server/search.clj");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
		Var searchFunction = RT.var("de.cr.freitonal.server.search", "doSearch");
		String result;
		try {
			result = (String) searchFunction.invoke(confDir.getAbsolutePath() + "/db.clj", httpServletRequest.getParameterMap());
			httpServletResponse.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SearchServlet().init(null);
	}
}
