package de.cr.freitonal.unittests.client.widgets.base;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.widgets.base.SelectablePresenter;
import de.cr.freitonal.client.widgets.base.View;
import de.cr.freitonal.shared.models.Item;

public class SelectablePresenterMock implements SelectablePresenter {

	private DisplayMode mode;

	public void setDisplayMode(DisplayMode mode) {
		this.mode = mode;
	}

	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
	}

	public Item getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	public View getView() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setItems(ItemSet instruments) {
		// TODO Auto-generated method stub

	}

	public DisplayMode getDisplayMode() {
		return mode;
	}

}
