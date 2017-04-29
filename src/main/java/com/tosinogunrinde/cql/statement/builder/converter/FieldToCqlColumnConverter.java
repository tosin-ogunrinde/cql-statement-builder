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

import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.*;
import com.google.common.base.Strings;
import com.tosinogunrinde.cql.statement.builder.EntityType;
import com.tosinogunrinde.cql.statement.builder.util.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public final class FieldToCqlColumnConverter implements Converter<Field, String> {

    @Override
    public String convert(Field field) {
        ObjectValidator.validateNotNull(field);
        String cqlType = new TypeToCqlTypeConverter().convert(field.getType().getTypeName());
        if (!Strings.isNullOrEmpty(cqlType)) {
            return cqlType;
        }
        if (isEnumField(field)) {
            return getEnumType(field);
        }
        if (isListField(field)) {
            return new ListToCqlListConverter().convert(field);
        }
        if (isSetField(field)) {
            return new SetToCqlSetConverter().convert(field);
        }
        if (isMapField(field)) {
            return new MapToCqlMapConverter().convert(field);
        }
        if (isUDTField(field)) {
            return freezeField(field);
        }
        throw new UnsupportedOperationException("field is not supported. Did you forget to annotate with @Frozen? - type: " + field.getType());
    }

    private boolean isListField(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private boolean isSetField(Field field) {
        return Set.class.isAssignableFrom(field.getType());
    }

    private boolean isMapField(Field field) {
        return Map.class.isAssignableFrom(field.getType());
    }

    private boolean isEnumField(Field field) {
        return field.getType().isEnum();
    }

    private boolean isUDTField(Field field) {
        return AnnotationUtils.getClassAnnotationTypes(field.getType()).contains(EntityType.UDT.getAnnotation()) &&
                field.getAnnotation(Frozen.class) != null;
    }

    private String getEnumType(Field field) {
        Enumerated enumerated = field.getAnnotation(Enumerated.class);
        if (enumerated == null) {
            return "text";
        } else {
            return enumerated.value() == EnumType.STRING ? "text" : "int";
        }
    }

    private String freezeField(Field field) {
        return "frozen<" + field.getType().getSimpleName().toLowerCase() + ">";
    }
}