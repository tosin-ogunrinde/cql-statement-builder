package com.tosinogunrinde.cql.statement.builder;

import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class CountStatementBuilderTest {

    @Test
    public void createStatement() throws Exception {
        Assert.assertEquals("SELECT COUNT(*) FROM person WHERE firstName = 'foo' AND lastName = 'bar'", new CqlStatementBuildDirector().buildStatement(new CountStatementBuilder(Person.class, "foo", "bar")));
    }
}