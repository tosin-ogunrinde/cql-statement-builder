package com.tosinogunrinde.cql.statementbuilder.converter;

import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class TypeToCqlTypeConverterTest {

    @Test(expected = IllegalArgumentException.class)
    public void convertThrowExceptionIfNull() {
        new TypeToCqlTypeConverter().convert(null);
    }

    @Test
    public void convert() throws Exception {
        TypeToCqlTypeConverter typeToCqlTypeConverter = new TypeToCqlTypeConverter();
        Assert.assertEquals("decimal", typeToCqlTypeConverter.convert("java.math.BigDecimal"));
        Assert.assertEquals("float", typeToCqlTypeConverter.convert("java.lang.Float"));
        Assert.assertEquals("double", typeToCqlTypeConverter.convert("java.lang.Double"));
        Assert.assertEquals("varint", typeToCqlTypeConverter.convert("java.math.BigInteger"));
        Assert.assertEquals("text", typeToCqlTypeConverter.convert("java.lang.String"));
        Assert.assertEquals("int", typeToCqlTypeConverter.convert("java.lang.Integer"));
        Assert.assertEquals("boolean", typeToCqlTypeConverter.convert("boolean"));
        Assert.assertEquals("double", typeToCqlTypeConverter.convert("double"));
        Assert.assertEquals("double", typeToCqlTypeConverter.convert("java.lang.Double"));
        Assert.assertEquals("float", typeToCqlTypeConverter.convert("float"));
        Assert.assertEquals("bigint", typeToCqlTypeConverter.convert("long"));
        Assert.assertEquals("bigint", typeToCqlTypeConverter.convert("java.lang.Long"));
        Assert.assertEquals("timestamp", typeToCqlTypeConverter.convert("java.util.Date"));
        Assert.assertEquals("uuid", typeToCqlTypeConverter.convert("java.util.UUID"));
    }
}