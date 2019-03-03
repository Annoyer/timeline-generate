package org.jcy.timeline.util;

import org.junit.Assert;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.util.ThrowableCaptor.thrownBy;

public class MessagesTest {

    @Test
    public void get() {
        Assert.assertEquals("Top item is missing.", Messages.get("TOP_ITEM_IS_MISSING"));
    }

    @Test
    public void getWithNullPatternName() {
        Throwable actual = thrownBy(() -> Messages.get(null));

        assertThat(actual)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getWithUnknownPattern() {
        Assert.assertEquals("", Messages.get("???"));
    }

    @Test
    public void getWithArgs() {
        Assert.assertEquals("TopItem <ITEM> is not contained in item list.", Messages.get("ERROR_TOP_ITEM_IS_UNRELATED", "ITEM"));
    }

    @Test
    public void getWithArgsAndNullPatternName() {
        Throwable actual = thrownBy(() -> Messages.get(null, "ITEM"));

        assertThat(actual)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getWithArgsAndUnknownPattern() {
        Assert.assertEquals(Messages.get("??", "ITEM"), "");
    }

    @Test
    public void getWithArgsButHasNoFormatArgument() {
        Assert.assertEquals(Messages.get("TOP_ITEM_IS_MISSING", "ITEM"), Messages.get("TOP_ITEM_IS_MISSING"));
    }
}
