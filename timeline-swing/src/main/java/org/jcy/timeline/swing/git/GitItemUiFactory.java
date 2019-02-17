package org.jcy.timeline.swing.git;

import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.provider.git.GitItem;
import java.awt.Container;
import org.jcy.timeline.core.ui.ItemUi;
import org.jcy.timeline.util.Assertion;

import static org.jcy.timeline.swing.ui.SwingItemUi.createUiItemConstraints;

public class GitItemUiFactory implements ItemUiFactory<GitItem, Container> {

	/**
	 * Create an ui item with the specific commit item in the ui context component.
	 * @param uiContext the ui context component.
	 * @param item commit item.
	 * @param index the position in the context component's list at which to insert
	 */
	public ItemUi<GitItem> create(Container uiContext, GitItem item, int index) {
		Assertion.check(uiContext != null, "UI_CONTEXT_MUST_NOT_BE_NULL");
		Assertion.check(item != null, "ITEM_MUST_NOT_BE_NULL");
		Assertion.check(index >= 0, "INDEX_MUST_NOT_BE_NEGATIVE");

		// 创建的同时，就展示了这个commit item。
		GitItemUi result = new GitItemUi(item);
		uiContext.add(result.getComponent(), createUiItemConstraints(), index);
		return result;
	}

}