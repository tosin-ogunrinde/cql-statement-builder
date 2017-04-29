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
package com.tosinogunrinde.cql.statement.builder.util;

import com.datastax.driver.mapping.annotations.Column;
import com.google.common.base.Strings;
import com.tosinogunrinde.cql.statement.builder.EntityType;
import org.slf4j.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public class AnnotationUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AnnotationUtils.class);

    private AnnotationUtils() {
    }

    public static Set<String> getClassAnnotationTypes(Class<?> clazz) {
        ObjectValidator.validateNotNull(clazz);
        Set<String> annotations = new HashSet<>();
        for (Annotation annotation : clazz.getAnnotations()) {
            annotations.add(annotation.annotationType().getSimpleName());
        }
        return annotations;
    }

    public static String getEntityName(Class<?> clazz) {
        ObjectValidator.validateNotNull(clazz);
        String className = null;
        for (Annotation annotation : clazz.getAnnotations()) {
            String annotationName = annotation.annotationType().getSimpleName();
            if (annotationName.equals(EntityType.TABLE.getAnnotation()) || annotationName.equals(EntityType.UDT.getAnnotation())) {
                try {
                    className = getEntityName(annotation);
                } catch (ReflectiveOperationException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return className == null ? clazz.getSimpleName() : className;
    }

    private static String getEntityName(Annotation annotation) throws ReflectiveOperationException {
        String entityName = null;
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            Object value = method.invoke(annotation, (Object[]) null);
            if ("name".equals(method.getName())) {
                entityName = value.toString();
                break;
            }
        }
        return entityName;
    }

    public static String getFieldName(Field field) {
        ObjectValidator.validateNotNull(field);
        Column column = field.getAnnotation(Column.class);
        if (column != null && !Strings.isNullOrEmpty(column.name())) {
            return column.name();
        } else {
            return field.getName();
        }
    }

    public static List<Field> getFieldsAnnotatedWith(Class<?> clazz, List<Class<? extends Annotation>> fieldTypeAnnotations) {
        ObjectValidator.validateNotNull(clazz);
        CollectionValidator.validateNotNullOrEmpty(fieldTypeAnnotations);
        List<Field> annotatedFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            for (Class<? extends Annotation> annotation : fieldTypeAnnotations) {
                if (field.getAnnotation(annotation) != null) {
                    annotatedFields.add(field);
                }
            }
        }
        return annotatedFields;
    }
}