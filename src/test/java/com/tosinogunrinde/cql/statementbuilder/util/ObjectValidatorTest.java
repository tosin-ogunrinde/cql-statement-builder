package com.tosinogunrinde.cql.statementbuilder.util;

import com.tosinogunrinde.cql.statementbuilder.Person;
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