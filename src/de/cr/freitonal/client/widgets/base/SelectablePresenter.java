package de.cr.freitonal.client.widgets.base;

import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.shared.models.Item;

public interface SelectablePresenter extends Presenter {
	public Item getSelectedItem();

	public View getView();

	public void setItemSet(ItemSet itemSet);
}
