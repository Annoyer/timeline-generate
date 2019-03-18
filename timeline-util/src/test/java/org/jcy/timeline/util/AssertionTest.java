package org.jcy.timeline.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;

public class AssertionTest {

    private static final String MESSAGE_PATTERN = "MESSAGE_FOR_TEST";
    private static final String ARGUMENT_ONE = "one";
    private static final String ARGUMENT_TWO = "two";
    private static final String EXPECTED_MESSAGE = ARGUMENT_ONE + " " + ARGUMENT_TWO + " message";

    @Test
    public void formatErrorMessage() {
        String actual = Assertion.formatErrorMessage(MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO);

        assertThat(actual).isEqualTo(EXPECTED_MESSAGE);
    }

    @Test
    public void formatErrorMessageWithNullAsPattern() {
        Throwable actual = thrownBy(() -> Assertion.formatErrorMessage(null, ARGUMENT_ONE, ARGUMENT_TWO));

        assertThat(actual)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Message pattern name must not be null.");
    }

    @Test
    public void formatErrorMessageWithNullAsArgument() {
        String actual = Assertion.formatErrorMessage(MESSAGE_PATTERN);

        assertThat(actual).isEqualTo(Messages.get(MESSAGE_PATTERN));
    }

    @Test
    public void checkArgumentWithValidCondition() {
        Throwable actual = thrownBy(() -> Assertion.check(true, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO));

        assertThat(actual).isNull();
    }

    @Test
    public void checkStateWithValidCondition() {
        Throwable actual = thrownBy(() -> Assertion.check(true, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO));

        assertThat(actual).isNull();
    }

    @Test
    public void checkStateWithInvalidCondition() {
        Throwable actual = thrownBy(() -> Assertion.check(false, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO));

        assertThat(actual)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EXPECTED_MESSAGE);
    }
}