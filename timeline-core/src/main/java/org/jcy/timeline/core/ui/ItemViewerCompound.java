package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

/**
 * Compound of main UI components in the item viewer.
 *
 * @param <I>
 * @param <U>
 */
public interface ItemViewerCompound<I extends Item, U> {

	ItemUiList<I, U> getItemUiList();

	TopItemScroller<I> getScroller();

	TopItemUpdater<I, U> getTopItemUpdater();

}