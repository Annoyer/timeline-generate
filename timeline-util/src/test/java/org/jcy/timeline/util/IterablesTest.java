package org.jcy.timeline.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.util.ThrowableCaptor.thrownBy;

public class IterablesTest {

    @Test
    public void asList() {
        String[] expected = new String[]{"A", "B", "C"};
        Iterable<String> iterable = Arrays.asList(expected);

        List<String> actual = Iterables.asList(iterable);

        assertThat(actual)
                .isNotSameAs(iterable)
                .containsExactly(expected);
    }

    @Test
    public void asListWithNullAsIterable() {
        Throwable actual = thrownBy(() -> Iterables.asList(null));

        assertThat(actual)
                .hasMessage(Messages.get("ITERABLE_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}