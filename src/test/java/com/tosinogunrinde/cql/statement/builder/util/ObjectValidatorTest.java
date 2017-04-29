package com.tosinogunrinde.cql.statement.builder.util;

import com.tosinogunrinde.cql.statement.builder.Person;
import org.junit.Test;

/**
 * @author Tosin Ogunrinde
 */
public class ObjectValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void validateNotNullThrowExceptionIfNull() throws Exception {
        ObjectValidator.validateNotNull(null);
    }

    @Test
    public void validateNotNull() throws Exception {
        ObjectValidator.validateNotNull(new Person());
    }
}