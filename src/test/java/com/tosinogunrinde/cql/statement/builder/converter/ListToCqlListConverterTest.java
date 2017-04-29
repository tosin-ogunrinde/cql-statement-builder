package com.tosinogunrinde.cql.statement.builder.converter;

import org.junit.*;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author Tosin Ogunrinde
 */
public class ListToCqlListConverterTest {

    @Test
    public void convert() {
        Assert.assertEquals("list<text>", new ListToCqlListConverter().convert(PowerMockito.field(ConverterTestPojo.class, "stringList")));
        Assert.assertEquals("list<text>", new ListToCqlListConverter().convert(PowerMockito.field(ConverterTestPojo.class, "stringArrayList")));
        Assert.assertEquals("list<frozen<convertertestpojo>>", new ListToCqlListConverter().convert(PowerMockito.field(ConverterTestPojo.class, "converterTestPojoList")));
    }
}