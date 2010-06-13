package de.cr.freitonal;

import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

public class GWTSuite extends GWTTestSuite {
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("FreitonalGUI Tests");
		return suite;
	}
}
