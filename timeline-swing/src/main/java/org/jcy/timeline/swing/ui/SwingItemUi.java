package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemUi;
import java.awt.Component;
import java.awt.GridBagConstraints;

public interface SwingItemUi<I extends Item> extends ItemUi<I> {

	Component getComponent();

	static GridBagConstraints createUiItemConstraints(){

	}

}