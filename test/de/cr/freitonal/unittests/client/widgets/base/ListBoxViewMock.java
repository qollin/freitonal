package de.cr.freitonal.unittests.client.widgets.base;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.models.Item;
import de.cr.freitonal.client.widgets.base.ListBoxPresenter.View;

public class ListBoxViewMock implements View {

	private boolean enabled;

	public Item getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void removeFromParent() {
		// TODO Auto-generated method stub

	}

	public void setDisplayMode(DisplayMode mode) {
		// TODO Auto-generated method stub

	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setItems(ArrayList<Item> items) {
		// TODO Auto-generated method stub

	}

	public void setSelectedItem(Item selected) {
		// TODO Auto-generated method stub

	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public void fireEvent(GwtEvent<?> event) {
		// TODO Auto-generated method stub

	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

}
