package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.VolatileItem;

@RemoteServiceRelativePath("create")
public interface CreateService extends RemoteService {
	public Item createComposer(VolatileItem composer);
}
