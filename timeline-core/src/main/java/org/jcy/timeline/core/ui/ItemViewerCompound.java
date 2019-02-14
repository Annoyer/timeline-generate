package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

public interface ItemViewerCompound<I extends Item, U> {

	ItemUiList<I, U> getItemUiList();

	TopItemScroller<I> getScroller();

	TopItemUpdater<I, U> getTopItemUpdater();

}