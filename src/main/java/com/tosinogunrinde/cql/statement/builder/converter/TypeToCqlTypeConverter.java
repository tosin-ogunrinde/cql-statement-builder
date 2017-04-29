/*
 * Copyright (C) 2017 Tosin Ogunrinde
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tosinogunrinde.cql.statement.builder.converter;

import com.tosinogunrinde.cql.statement.builder.util.StringValidator;

import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public final class TypeToCqlTypeConverter implements Converter<String, String> {
    private static final String BOOLEAN = "boolean";
    private static final String VARINT = "varint";
    private static final String INT = "int";
    private static final String TEXT = "text";
    private static final String DOUBLE = "double";
    private static final String FLOAT = "float";
    private static final String DECIMAL = "decimal";
    private static final String LONG = "long";
    private static final String BIGINT = "bigint";
    private static final String TIMESTAMP = "timestamp";
    private static final String UUID = "uuid";
    private static final String JAVA_UTIL_UUID = "java.util.UUID";
    private static final String JAVA_UTIL_DATE = "java.util.Date";
    private static final String JAVA_LANG_INTEGER = "java.lang.Integer";
    private static final String JAVA_MATH_BIGINTEGER = "java.math.BigInteger";
    private static final String JAVA_LANG_BOOLEAN = "java.lang.Boolean";
    private static final String JAVA_LANG_STRING = "java.lang.String";
    private static final String JAVA_LANG_DOUBLE = "java.lang.Double";
    private static final String JAVA_LANG_FLOAT = "java.lang.Float";
    private static final String JAVA_MATH_BIGDECIMAL = "java.math.BigDecimal";
    private static final String JAVA_LANG_LONG = "java.lang.Long";
    private final Map<String, String> typesMap;

    public TypeToCqlTypeConverter() {
        typesMap = new HashMap<>();
        typesMap.put(JAVA_LANG_STRING, TEXT);
        typesMap.put(JAVA_LANG_INTEGER, INT);
        typesMap.put(INT, INT);
        typesMap.put(JAVA_MATH_BIGINTEGER, VARINT);
        typesMap.put(BOOLEAN, BOOLEAN);
        typesMap.put(JAVA_LANG_BOOLEAN, BOOLEAN);
        typesMap.put(DOUBLE, DOUBLE);
        typesMap.put(JAVA_LANG_DOUBLE, DOUBLE);
        typesMap.put(FLOAT, FLOAT);
        typesMap.put(JAVA_LANG_FLOAT, FLOAT);
        typesMap.put(JAVA_MATH_BIGDECIMAL, DECIMAL);
        typesMap.put(LONG, BIGINT);
        typesMap.put(JAVA_LANG_LONG, BIGINT);
        typesMap.put(JAVA_UTIL_DATE, TIMESTAMP);
        typesMap.put(JAVA_UTIL_UUID, UUID);
    }

    @Override
    public String convert(String type) {
        StringValidator.validateNotNullOrEmpty(type);
        return typesMap.get(type);
    }
}