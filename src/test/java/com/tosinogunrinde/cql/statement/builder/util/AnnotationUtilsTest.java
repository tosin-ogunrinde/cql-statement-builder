package com.tosinogunrinde.cql.statement.builder.util;

import com.tosinogunrinde.cql.statement.builder.*;
import org.junit.*;

/**
 * @author Tosin Ogunrinde
 */
public class AnnotationUtilsTest {

    @Test
    public void getClassAnnotationTypes() throws Exception {
        Assert.assertTrue(AnnotationUtils.getClassAnnotationTypes(UsersByUsername.class).contains(EntityType.TABLE.getAnnotation()));
        Assert.assertTrue(AnnotationUtils.getClassAnnotationTypes(Address.class).contains(EntityType.UDT.getAnnotation()));
    }

    @Test
    public void getEntityName() throws Exception {
        Assert.assertEquals("address", AnnotationUtils.getEntityName(Address.class));
        Assert.assertEquals("users_by_username", AnnotationUtils.getEntityName(UsersByUsername.class));
        Assert.assertEquals("ClassWithNoAnnotation", AnnotationUtils.getEntityName(ClassWithNoAnnotation.class));
    }
}