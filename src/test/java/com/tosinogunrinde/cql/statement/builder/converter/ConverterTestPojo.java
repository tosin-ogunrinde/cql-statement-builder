package com.tosinogunrinde.cql.statement.builder.converter;

import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
@SuppressWarnings("unused")
class ConverterTestPojo {

    private List<String> stringList;
    private ArrayList<String> stringArrayList;
    private List<ConverterTestPojo> converterTestPojoList;

    private Set<String> stringSet;
    private HashSet<String> stringHashSet;
    private Set<ConverterTestPojo> converterTestPojoSet;

    private Map<String, String> stringStringMap;
    private HashMap<String, String> stringStringHashMap;
    private Map<String, ConverterTestPojo> converterTestPojoMap;
    private Map<ConverterTestPojo, ConverterTestPojo> converterTestPojoConverterTestPojoMap;
}