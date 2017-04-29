package com.tosinogunrinde.cql.statement.builder.schemastatementbuilder;

import com.datastax.driver.mapping.annotations.UDT;

/**
 * @author Tosin Ogunrinde
 */
@UDT(name = "fooBarOne")
public class FooBarOne {
    private int target;
}