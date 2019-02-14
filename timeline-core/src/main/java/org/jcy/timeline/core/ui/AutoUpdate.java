package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

public interface AutoUpdate<I extends Item, U> {

	void start();

	void stop();

}