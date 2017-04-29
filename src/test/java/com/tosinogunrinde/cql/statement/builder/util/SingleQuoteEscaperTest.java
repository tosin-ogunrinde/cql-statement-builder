package com.tosinogunrinde.cql.statement.builder.util;

import org.junit.*;

import java.util.Date;

/**
 * @author Tosin Ogunrinde
 */
public class SingleQuoteEscaperTest {

    @Test(expected = IllegalArgumentException.class)
    public void escapeValueThrowExceptionIfNull() throws Exception {
        Assert.assertEquals("'foo'", SingleQuoteEscaper.escapeDateOrStringValue(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void escapeValueThrowExceptionIfNotStringOrDate() throws Exception {
        Assert.assertEquals("'foo'", SingleQuoteEscaper.escapeDateOrStringValue(1));
    }

    @Test
    public void escapeValueString() throws Exception {
        Assert.assertEquals("'foo'", SingleQuoteEscaper.escapeDateOrStringValue("foo"));
    }

    @Test
    public void escapeValueDate() throws Exception {
        String escapedValue = SingleQuoteEscaper.escapeDateOrStringValue(new Date());
        Assert.assertTrue(escapedValue.startsWith("'"));
        Assert.assertTrue(escapedValue.endsWith("'"));
    }
}