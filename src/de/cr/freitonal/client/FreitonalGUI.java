package de.cr.freitonal.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.client.rpc.URLEncoderImpl;
import de.cr.freitonal.client.rpc.gwt.DTOParserGWT;
import de.cr.freitonal.client.widgets.piece.PieceView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FreitonalGUI implements EntryPoint {
	interface Binder extends UiBinder<HTMLPanel, FreitonalGUI> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	PieceView pieceView;

	private RPCService rpcService;
	private AppController appController;

	public void onModuleLoad() {
		HTMLPanel htmlPanel = binder.createAndBindUi(this);

		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(htmlPanel);

		rpcService = new RPCServiceImpl(new DTOParserGWT(), new URLEncoderImpl(GWT.getHostPageBaseURL()));
		appController = new AppController(pieceView, rpcService);
		appController.go();
	}
}
