package org.jcy.timeline.core.model;

import java.util.List;

public interface ItemProvider<I extends Item> {

    List<I> fetchItems(I ancestor, int fetchCount);

    int getNewCount(I predecessor);

    List<I> fetchNew(I predecessor);
}