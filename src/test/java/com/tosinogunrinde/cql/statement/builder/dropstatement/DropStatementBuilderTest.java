package com.tosinogunrinde.cql.statement.builder.dropstatement;

import com.tosinogunrinde.cql.statement.builder.CqlStatementBuildDirector;
import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class DropStatementBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfEntityTypeIsUnspecified() throws Exception {
        new DropStatementBuilder(null, "foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfEntityNameIsUnspecified() throws Exception {
        new DropStatementBuilder(DroppableItem.KEYSPACE, null);
    }

    @Test
    public void getStatement() throws Exception {
        Assert.assertEquals("DROP KEYSPACE IF EXISTS foo", new CqlStatementBuildDirector().buildStatement(new DropStatementBuilder(DroppableItem.KEYSPACE, "foo")));
        Assert.assertEquals("DROP TABLE IF EXISTS foo", new CqlStatementBuildDirector().buildStatement(new DropStatementBuilder(DroppableItem.TABLE, "foo")));
        Assert.assertEquals("DROP TYPE IF EXISTS foo", new CqlStatementBuildDirector().buildStatement(new DropStatementBuilder(DroppableItem.TYPE, "foo")));
        Assert.assertEquals("DROP INDEX IF EXISTS foo", new CqlStatementBuildDirector().buildStatement(new DropStatementBuilder(DroppableItem.INDEX, "foo")));
    }
}