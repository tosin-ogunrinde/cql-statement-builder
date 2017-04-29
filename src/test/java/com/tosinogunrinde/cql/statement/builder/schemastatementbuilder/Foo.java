package com.tosinogunrinde.cql.statement.builder.schemastatementbuilder;

import com.datastax.driver.mapping.annotations.*;

import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
@Table(name = "foo")
public class Foo {
    @PartitionKey
    private UUID id;
    private int firstName;
    private Date dob;

    @FrozenValue
    private Map<String, Bar> bars;

    private List<String> stringList;

    @Frozen
    private FooBarTwo fooBarTwo;

    @FrozenValue
    private List<Bar> bar;

    @FrozenKey
    @FrozenValue
    private Map<Bar, List<Bar>> barsTwo;
}