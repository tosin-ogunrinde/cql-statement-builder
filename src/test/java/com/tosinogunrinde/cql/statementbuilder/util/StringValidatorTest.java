package com.tosinogunrinde.cql.statementbuilder.util;

import org.junit.Test;

/**
 * @author Tosin Ogunrinde
 */
public class StringValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateNotEmptyThrowExceptionIfNull() throws Exception {
        StringValidator.validateNotNullOrEmpty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateNotEmptyThrowExceptionIfEmpty() throws Exception {
        StringValidator.validateNotNullOrEmpty("");
    }

    @Test
    public void validateNotEmpty() throws Exception {
        StringValidator.validateNotNullOrEmpty("foo");
    }
}