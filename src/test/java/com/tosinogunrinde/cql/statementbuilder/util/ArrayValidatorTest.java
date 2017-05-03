package com.tosinogunrinde.cql.statementbuilder.util;

import org.junit.Test;

/**
 * @author Tosin Ogunrinde
 */
public class ArrayValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateNotNullOrEmptyThrowExceptionIfNull() throws Exception {
        ArrayValidator.validateNotNullOrEmpty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateNotNullOrEmptyThrowExceptionIfEmpty() throws Exception {
        ArrayValidator.validateNotNullOrEmpty(new String[0]);
    }

    @Test
    public void validateNotNullOrEmpty() throws Exception {
        ArrayValidator.validateNotNullOrEmpty(new String[]{"foo"});
    }
}