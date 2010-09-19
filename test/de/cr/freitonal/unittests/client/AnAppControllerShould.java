package de.cr.freitonal.unittests.client;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.AppController;
import de.cr.freitonal.unittests.client.rpc.RPCServiceMock;
import de.cr.freitonal.unittests.client.widgets.piece.PieceViewMock;

public class AnAppControllerShould {
	private final ArrayList<String> trace = new ArrayList<String>();

	@Before
	public void setup() {
		trace.clear();
	}

	@Test
	public void CallSearchWhenAnSearchFieldChangedEventIsReceived() {
		PieceViewMock pieceViewMock = new PieceViewMock();
		AppController appController = new AppController(pieceViewMock, new RPCServiceMock(trace));

	}
}
