package org.jcy.timeline.util;

import org.junit.Test;

import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;

public class NiceTimeTest {

    @Test
    public void formatNow() {
        String actual = NiceTime.format(now());

        assertThat(actual).contains("moments");
    }

    @Test
    public void formatFiveMinutesAgo() {
        String actual = NiceTime.format(fiveMinutesAgo());

        assertThat(actual).contains("5 minutes ago");
    }

    private long fiveMinutesAgo() {
        return currentTimeMillis() - 5 * 1_000 * 60;
    }

    private long now() {
        return currentTimeMillis();
    }
}