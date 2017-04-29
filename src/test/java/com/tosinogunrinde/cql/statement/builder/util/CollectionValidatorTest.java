package com.tosinogunrinde.cql.statement.builder.util;

import org.junit.Test;

import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public class CollectionValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateNotNullOrEmptyThrowExceptionIfNull() throws Exception {
        CollectionValidator.validateNotNullOrEmpty(new ArrayList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateNotNullOrEmptyThrowExceptionIfEmpty() throws Exception {
        CollectionValidator.validateNotNullOrEmpty(new ArrayList());
    }

    @Test
    public void validateNotNullOrEmpty() throws Exception {
        CollectionValidator.validateNotNullOrEmpty(Collections.singletonList("foo"));
    }
}