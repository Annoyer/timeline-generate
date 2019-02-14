package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemUi;

import java.awt.*;

import java.awt.GridBagConstraints;

public interface SwingItemUi<I extends Item> extends ItemUi<I> {

	Component getComponent();

	static GridBagConstraints createUiItemConstraints(){
		GridBagConstraints result = new GridBagConstraints();
		result.gridx = 0;
		result.fill = GridBagConstraints.HORIZONTAL;
		result.weightx = 1;
		result.insets = new Insets(15, 10, 5, 10);
		return result;
	}

}