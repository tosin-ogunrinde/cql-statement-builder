package com.tosinogunrinde.cql.statement.builder;

import com.datastax.driver.mapping.annotations.*;

import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
@SuppressWarnings("unused")
public class ClassWithNoAnnotation {

    @Frozen
    private Address frozenAddress;

    private Address unfrozenAddress;

    @FrozenValue
    private List<Address> addressesOne;

    @FrozenKey
    @FrozenValue
    private Set<Address> addressesTwo;

    @FrozenValue
    private Map<String, Address> addressThree;

    @FrozenKey
    @FrozenValue
    private Map<Address, Address> addressFour;

    @FrozenKey
    @FrozenValue
    private Map<Address, List<Address>> addressFive;

    @FrozenKey
    @FrozenValue
    private Map<Address, Set<String>> addressTen;

    @FrozenKey
    @FrozenValue
    private Map<Address, List<String>> addressNine;

    @FrozenKey
    @FrozenValue
    private Map<Address, Map<String, Address>> addressSix;

    @FrozenKey
    @FrozenValue
    private Map<Address, Map<Address, Address>> addressSeven;
}