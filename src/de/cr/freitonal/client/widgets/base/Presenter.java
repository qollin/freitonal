package de.cr.freitonal.client.widgets.base;

import de.cr.freitonal.client.event.DisplayMode;

public interface Presenter {
	public void setDisplayMode(DisplayMode mode);

	public DisplayMode getDisplayMode();

	public void setEnabled(boolean enabled);

}
