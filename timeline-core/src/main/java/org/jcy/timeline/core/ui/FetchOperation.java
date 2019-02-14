package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.model.Item;

import org.jcy.timeline.util.Assertion;

/**
 * Fetch Operation.
 */
public enum FetchOperation {

	/**
	 * 后台线程每5s尝试fetchNewCount，如有新的commit，在Header上显示‘new’按钮。
	 * 点击new按钮后执行此操作。
	 * 拉取 缓存中最新一条更新之后的 最新的最多100条更新
	 */
	NEW {
		@Override
		public <I extends Item> void fetch(Timeline<I> timeline) {
			Assertion.check(timeline != null, "TIMELINE_MUST_NOT_BE_NULL");

			timeline.fetchNew();
		}
	},

	/**
	 * 初始化和点击more按钮时执行的操作。
	 * 拉取 缓存中最旧的一条记录开始，更旧的10条更新记录。
	 */
	MORE {
		@Override
		public <I extends Item> void fetch(Timeline<I> timeline) {
			Assertion.check(timeline != null, "TIMELINE_MUST_NOT_BE_NULL");

			timeline.fetchItems();
		}
	};

	/**
	 * Fetch the git commits.
	 *
	 * @param timeline current timeline.
	 */
	public abstract <I extends Item>void fetch(Timeline<I> timeline);

}