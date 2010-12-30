package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("purge")
public interface PurgeService extends RemoteService {
	public void purgeDB();
}
