package de.cr.freitonal.usertests;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

import de.cr.freitonal.usertests.gui.CreatePieceTest;
import de.cr.freitonal.usertests.gui.PieceListTest;
import de.cr.freitonal.usertests.gui.SearchTest;

public class UserTestSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Usertests");
		suite.addTestSuite(SearchTest.class);
		suite.addTestSuite(CreatePieceTest.class);
		suite.addTestSuite(PieceListTest.class);

		return suite;
	}
}
