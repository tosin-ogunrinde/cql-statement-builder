package com.tosinogunrinde.cql.statement.builder.converter;

import org.junit.*;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author Tosin Ogunrinde
 */
public class SetToCqlSetConverterTest {

    @Test
    public void convert() {
        Assert.assertEquals("set<text>", new SetToCqlSetConverter().convert(PowerMockito.field(ConverterTestPojo.class, "stringSet")));
        Assert.assertEquals("set<text>", new SetToCqlSetConverter().convert(PowerMockito.field(ConverterTestPojo.class, "stringHashSet")));
        Assert.assertEquals("set<frozen<convertertestpojo>>", new SetToCqlSetConverter().convert(PowerMockito.field(ConverterTestPojo.class, "converterTestPojoSet")));
    }
}