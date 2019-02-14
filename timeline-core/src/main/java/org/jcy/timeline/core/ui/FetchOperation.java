package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.model.Item;

import static org.jcy.timeline.util.Assertion.checkArgument;

public enum FetchOperation {

	NEW {
		@Override
		public <I extends Item> void fetch(Timeline<I> timeline) {
			checkArgument(timeline != null, TIMELINE_MUST_NOT_BE_NULL);

			timeline.fetchNew();
		}
	},

	MORE {
		@Override
		public <I extends Item> void fetch(Timeline<I> timeline) {
			checkArgument(timeline != null, TIMELINE_MUST_NOT_BE_NULL);

			timeline.fetchItems();
		}
	};

	static final String TIMELINE_MUST_NOT_BE_NULL = "Argument 'timeline' must not be null.";

	/**
	 *
	 * @param timeline
	 */
	public abstract <I extends Item>void fetch(Timeline<I> timeline);

}