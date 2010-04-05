package de.cr.freitonal.tests;

import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

import de.cr.freitonal.client.rpc.ModelFactoryTest;
import de.cr.freitonal.client.rpc.RPCServiceImplTest;
import de.cr.freitonal.client.widgets.piece.JSONPieceTest;

public class GWTSuite extends GWTTestSuite {
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("FreitonalGUI Tests");
		suite.addTestSuite(ModelFactoryTest.class);
		suite.addTestSuite(JSONPieceTest.class);
		suite.addTestSuite(RPCServiceImplTest.class);
		return suite;
	}
}
