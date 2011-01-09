package de.cr.freitonal.unittests.client.widgets.base.listbox;

import java.util.ArrayList;

import de.cr.freitonal.client.widgets.base.listbox.IEditableListBoxView;

public class EditableListBoxViewMock extends ListBoxViewMock implements IEditableListBoxView {
	private String text;

	public EditableListBoxViewMock(ArrayList<String> trace) {
		super(trace);
	}

	public EditableListBoxViewMock() {
	}

	@Override
	public void setEnteredText(String text) {
		trace.add("setEnteredText:" + text);
		this.text = text;
	}

	@Override
	public String getText() {
		trace.add("getText");
		return text;
	}

}
