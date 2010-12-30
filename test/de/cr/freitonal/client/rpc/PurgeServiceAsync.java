package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PurgeServiceAsync {
	public void purgeDB(AsyncCallback<Void> callback);
}
