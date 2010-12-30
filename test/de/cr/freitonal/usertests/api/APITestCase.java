package de.cr.freitonal.usertests.api;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.client.rpc.PurgeService;
import de.cr.freitonal.client.rpc.PurgeServiceAsync;
import de.cr.freitonal.client.rpc.RPCService;
import de.cr.freitonal.client.rpc.RPCServiceImpl;
import de.cr.freitonal.shared.models.VolatileItem;
import de.cr.freitonal.usertests.api.ScriptSequence.Script;

public class APITestCase extends GWTTestCase {
	private ScriptSequence scripts;
	private RPCService rpcService;
	private PurgeServiceAsync purgeService;

	@Override
	public String getModuleName() {
		return "de.cr.freitonal.FreitonalGUI";
	}

	@Override
	public void gwtSetUp() {
		Script onFailureScript = new Script() {
			@Override
			public void run(Object... parameters) {
				fail();
				finishTest();
			}
		};

		scripts = new ScriptSequence(onFailureScript);
		rpcService = new RPCServiceImpl();
		purgeService = (PurgeServiceAsync) GWT.create(PurgeService.class);
		addPurgeDBScript();
	}

	private void purgeDB(AsyncCallback<Void> callback) {
		purgeService.purgeDB(callback);
	}

	private void addPurgeDBScript() {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				purgeDB(scripts.createVoidCallback());
			}
		});
	}

	protected void createComposer(final VolatileItem composer) {
		addScript(new Script() {
			@Override
			public void run(Object... parameters) {
				rpcService.createComposer(composer, scripts.createItemCallback());
			}
		});
	}

	protected ScriptSequence getScriptSequence() {
		return scripts;
	}

	protected void addScript(Script script) {
		scripts.addScript(script);
	}

	protected void go() {
		scripts.run();
		delayTestFinish(1000 * 10);
	}

}
