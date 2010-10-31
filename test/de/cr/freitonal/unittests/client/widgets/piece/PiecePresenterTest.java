package de.cr.freitonal.unittests.client.widgets.piece;

import de.cr.freitonal.unittests.client.rpc.RPCServiceMock;
import de.cr.freitonal.unittests.client.widgets.PresenterTest;

public class PiecePresenterTest extends PresenterTest {
	protected PieceViewMock view = new PieceViewMock();
	protected RPCServiceMock rpcService = new RPCServiceMock(trace);
}
