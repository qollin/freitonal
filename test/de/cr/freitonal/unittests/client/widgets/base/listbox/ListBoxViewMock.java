package de.cr.freitonal.unittests.client.widgets.base.listbox;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import de.cr.freitonal.client.event.DisplayMode;
import de.cr.freitonal.client.widgets.base.listbox.ListBoxPresenter.View;
import de.cr.freitonal.shared.models.Item;

public class ListBoxViewMock implements View {

	private boolean enabled;
	private ArrayList<String> trace = new ArrayList<String>();

	public ListBoxViewMock() {
	}

	public ListBoxViewMock(ArrayList<String> trace) {
		this.trace = trace;
	}

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
		trace.add("setDisplayMode:" + mode);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setItems(ArrayList<Item> items) {
		trace.add("setItems:" + items);
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
