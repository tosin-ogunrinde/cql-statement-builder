package com.tosinogunrinde.cql.statement.builder.entitystatement;

import org.junit.*;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

/**
 * @author Tosin Ogunrinde
 */
public class SchemaStatementGeneratorTest {

    @Test
    public void generateStatement() throws Exception {
        Assert.assertThat(new SchemaStatementGenerator("com.tosinogunrinde.cql.statement.builder.schemastatementbuilder").generateStatements(),
                containsInAnyOrder("CREATE TYPE IF NOT EXISTS fooBarOne (target int)",
                        "CREATE TYPE IF NOT EXISTS bar (fooBarOnes list<frozen<foobarone>>)",
                        "CREATE TYPE IF NOT EXISTS fooBarTwo (enumOne text)",
                        "CREATE TABLE IF NOT EXISTS foo (id uuid, firstName int, dob timestamp, bars map<text, frozen<bar>>, stringList list<text>, fooBarTwo frozen<foobartwo>, bar list<frozen<bar>>, barsTwo map<frozen<bar>, frozen<list<bar>>>, PRIMARY KEY (id))"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateStatementThrowExceptionIfEmptyBasePackage() throws Exception {
        new SchemaStatementGenerator("com.test").generateStatements();
    }
}