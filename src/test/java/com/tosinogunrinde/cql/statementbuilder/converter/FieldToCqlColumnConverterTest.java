package com.tosinogunrinde.cql.statementbuilder.converter;

import com.tosinogunrinde.cql.statementbuilder.*;
import org.junit.*;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Field;

/**
 * @author Tosin Ogunrinde
 */
public class FieldToCqlColumnConverterTest {

    @Test(expected = IllegalArgumentException.class)
    public void convertThrowExceptionIfNull() throws Exception {
        new FieldToCqlColumnConverter().convert(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void convertWithoutFrozenAnnotation() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "unfrozenAddress");
        new FieldToCqlColumnConverter().convert(field);
    }

    @Test
    public void convertUserDefineTypeWithFrozenAnnotation() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "frozenAddress");
        Assert.assertEquals("frozen<address>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertEnumString() throws Exception {
        Field field = PowerMockito.field(UsersByUsername.class, "gender");
        Assert.assertEquals("text", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertEnumOrdinal() throws Exception {
        Field field = PowerMockito.field(UsersByUsername.class, "country");
        Assert.assertEquals("int", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertList() throws Exception {
        Field field = PowerMockito.field(UsersByUsername.class, "locationIds");
        Assert.assertEquals("list<text>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertListWithUserDefinedType() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressesOne");
        Assert.assertEquals("list<frozen<address>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertSetType() throws Exception {
        Field field = PowerMockito.field(UsersByUsername.class, "campaignIds");
        Assert.assertEquals("set<text>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertSetWithUserDefinedType() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressesTwo");
        Assert.assertEquals("set<frozen<address>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMap() throws Exception {
        Field field = PowerMockito.field(UsersByUsername.class, "parents");
        Assert.assertEquals("map<text, text>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKey() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressThree");
        Assert.assertEquals("map<text, frozen<address>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKeyAndValue() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressFour");
        Assert.assertEquals("map<frozen<address>, frozen<address>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKeyAndUserDefinedList() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressFive");
        Assert.assertEquals("map<frozen<address>, frozen<list<address>>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKeyAndUserDefinedListr() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressTen");
        Assert.assertEquals("map<frozen<address>, frozen<set<text>>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKeyAndUserDefinedList4() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressFive");
        Assert.assertEquals("map<frozen<address>, frozen<list<address>>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKeyAndUserDefinedList2() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressNine");
        Assert.assertEquals("map<frozen<address>, frozen<list<text>>>", new FieldToCqlColumnConverter().convert(field));
    }

    @Test
    public void convertMapWithUserDefinedKeyAndUserDefinedList3() throws Exception {
        Field field = PowerMockito.field(ClassWithNoAnnotation.class, "addressSeven");
        Assert.assertEquals("map<frozen<address>, frozen<map<address, address>>>", new FieldToCqlColumnConverter().convert(field));
    }
}