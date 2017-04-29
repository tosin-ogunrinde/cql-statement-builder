package com.tosinogunrinde.cql.statement.builder.entitystatement;

import com.tosinogunrinde.cql.statement.builder.*;
import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class EntityStatementBuilderFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void getEntityStatementBuilderThrowExceptionIfUnspecifiedEntity() {
        new EntityStatementBuilderFactory(null).getEntityStatementBuilder();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getEntityStatementBuilderThrowExceptionIfInvalidAnnotation() {
        new EntityStatementBuilderFactory(ClassWithNoAnnotation.class).getEntityStatementBuilder();
    }

    @Test
    public void getEntityStatementBuilder() throws Exception {
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS organisations (id text, firstName text, lastName text, PRIMARY KEY (id, firstName, lastName)) WITH CLUSTERING ORDER BY (firstName ASC, lastName DESC)", new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(Organisation.class).getEntityStatementBuilder()));
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS users_by_username (username text, organisation_id text, gender text, country int, createdDate timestamp, locationIds list<text>, campaignIds set<text>, parents map<text, text>, addresses map<text, frozen<address>>, PRIMARY KEY (username))", new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(UsersByUsername.class).getEntityStatementBuilder()));
        Assert.assertEquals("CREATE TYPE IF NOT EXISTS address (street text, city text, zip int)", new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(Address.class).getEntityStatementBuilder()));
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS person (firstName text, lastName text, age int, PRIMARY KEY ((firstName, lastName), age)) WITH CLUSTERING ORDER BY (age ASC)", new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(Person.class).getEntityStatementBuilder()));
    }
}