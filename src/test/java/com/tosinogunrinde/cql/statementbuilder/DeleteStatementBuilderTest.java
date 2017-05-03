package com.tosinogunrinde.cql.statementbuilder;

import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class DeleteStatementBuilderTest {

    @Test
    public void createStatement() {
        Assert.assertEquals("DELETE FROM person WHERE firstName = 'foo' AND lastName = 'bar' AND age = 26", new CqlStatementBuildDirector().buildStatement(new DeleteStatementBuilder(Person.class, "foo", "bar", 26)));
    }
}