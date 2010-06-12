package de.cr.freitonal;

import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

import de.cr.freitonal.usertests.client.rpc.ModelFactoryTest;
import de.cr.freitonal.usertests.client.rpc.RPCServiceImplTest;
import de.cr.freitonal.usertests.client.widgets.base.ListBoxPresenterTest;
import de.cr.freitonal.usertests.client.widgets.catalog.CatalogPresenterTest;
import de.cr.freitonal.usertests.client.widgets.composer.ComposerPresenterTest;
import de.cr.freitonal.usertests.client.widgets.instrumentation.InstrumentationPresenterTest;
import de.cr.freitonal.usertests.client.widgets.musickey.MusicKeyPresenterTest;
import de.cr.freitonal.usertests.client.widgets.ordinal.OrdinalPresenterTest;
import de.cr.freitonal.usertests.client.widgets.piece.JSONPieceTest;
import de.cr.freitonal.usertests.client.widgets.piece.PieceCreationTest;
import de.cr.freitonal.usertests.client.widgets.subtitle.SubtitlePresenterTest;

public class GWTSuite extends GWTTestSuite {
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("FreitonalGUI Tests");
		suite.addTestSuite(ModelFactoryTest.class);
		suite.addTestSuite(ListBoxPresenterTest.class);
		suite.addTestSuite(JSONPieceTest.class);
		suite.addTestSuite(RPCServiceImplTest.class);
		suite.addTestSuite(ComposerPresenterTest.class);
		suite.addTestSuite(InstrumentationPresenterTest.class);
		suite.addTestSuite(CatalogPresenterTest.class);
		suite.addTestSuite(SubtitlePresenterTest.class);
		suite.addTestSuite(OrdinalPresenterTest.class);
		suite.addTestSuite(MusicKeyPresenterTest.class);
		suite.addTestSuite(PieceCreationTest.class);
		return suite;
	}
}
