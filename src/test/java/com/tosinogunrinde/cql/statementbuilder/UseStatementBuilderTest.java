package com.tosinogunrinde.cql.statementbuilder;

import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class UseStatementBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void buildStatementThrowExceptionIfNullKeyspace() throws Exception {
        new CqlStatementBuildDirector().buildStatement(new UseStatementBuilder(null));
    }

    @Test
    public void buildStatement() throws Exception {
        Assert.assertEquals("USE foo", new CqlStatementBuildDirector().buildStatement(new UseStatementBuilder("foo")));
    }
}