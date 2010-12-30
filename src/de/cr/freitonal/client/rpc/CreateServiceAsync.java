package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileItem;

public interface CreateServiceAsync {
	public void createComposer(VolatileItem composer, AsyncCallback<Item> callback);
}
