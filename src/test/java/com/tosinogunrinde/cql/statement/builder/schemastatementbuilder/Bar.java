package com.tosinogunrinde.cql.statement.builder.schemastatementbuilder;

import com.datastax.driver.mapping.annotations.*;

import java.util.List;

/**
 * @author Tosin Ogunrinde
 */
@UDT(name = "bar")
public class Bar {
    @FrozenValue
    private List<FooBarOne> fooBarOnes;
}