package com.tosinogunrinde.cql.statementbuilder;

import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class SelectStatementBuilderTest {

    @Test
    public void createStatement() {
        Assert.assertEquals("SELECT * FROM person WHERE firstName = 'foo' AND lastName = 'bar'", new CqlStatementBuildDirector().buildStatement(new SelectStatementBuilder(Person.class, "foo", "bar")));
    }
}