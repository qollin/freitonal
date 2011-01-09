package de.cr.freitonal.client.widgets.base.listbox;

import static de.cr.freitonal.client.event.DisplayMode.Create;

import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.widgets.base.SearchFieldPresenter;
import de.cr.freitonal.shared.models.Item;

public class EditableSearchFieldPresenter extends SearchFieldPresenter {
	private final IEditableListBoxView editableView;

	public EditableSearchFieldPresenter(EventBus eventBus, IEditableListBoxView view) {
		super(eventBus, view);
		this.editableView = view;
	}

	@Override
	public void fireOnNewItemSelected(Item selectedItem) {
		if (getDisplayMode() == Create) {
			throw new IllegalStateException("An editable search field has nothing to select in Create mode");
		}
		super.fireOnNewItemSelected(selectedItem);
	}

	public void setEnteredText(String value) {
		editableView.setEnteredText(value);
	}

	public String getText() {
		String text = editableView.getText();
		if (text == null || text.equals("")) {
			return null;
		} else {
			return text;
		}
	}

	@Override
	public Item getSelectedItem() {
		if (getDisplayMode() == Create) {
			String text = getText();
			if (text == null) {
				return null;
			} else {
				return new Item("fakefake", text);
			}
		}
		return super.getSelectedItem();
	}
}
