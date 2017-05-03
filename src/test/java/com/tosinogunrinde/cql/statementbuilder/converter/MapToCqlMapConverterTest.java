package com.tosinogunrinde.cql.statementbuilder.converter;

import org.junit.*;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author Tosin Ogunrinde
 */
public class MapToCqlMapConverterTest {

    @Test
    public void convert() {
        Assert.assertEquals("map<text, text>", new MapToCqlMapConverter().convert(PowerMockito.field(ConverterTestPojo.class, "stringStringMap")));
        Assert.assertEquals("map<text, text>", new MapToCqlMapConverter().convert(PowerMockito.field(ConverterTestPojo.class, "stringStringHashMap")));
        Assert.assertEquals("map<text, frozen<convertertestpojo>>", new MapToCqlMapConverter().convert(PowerMockito.field(ConverterTestPojo.class, "converterTestPojoMap")));
        Assert.assertEquals("map<frozen<convertertestpojo>, frozen<convertertestpojo>>", new MapToCqlMapConverter().convert(PowerMockito.field(ConverterTestPojo.class, "converterTestPojoConverterTestPojoMap")));
    }
}