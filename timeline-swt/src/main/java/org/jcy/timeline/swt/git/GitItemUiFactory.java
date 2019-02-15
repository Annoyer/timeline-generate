package org.jcy.timeline.swt.git;

import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.provider.git.GitItem;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.ui.ItemUi;
import org.jcy.timeline.util.Assertion;

public class GitItemUiFactory implements ItemUiFactory<GitItem, Composite> {

	public ItemUi<GitItem> create(Composite uiContext, GitItem item, int index) {
		Assertion.check(uiContext != null, "UI_CONTEXT_MUST_NOT_BE_NULL");
		Assertion.check(item != null, "ITEM_MUST_NOT_BE_NULL");
		Assertion.check(index >= 0, "INDEX_MUST_NOT_BE_NEGATIVE");

		return new GitItemUi(uiContext, item, index);
	}

}