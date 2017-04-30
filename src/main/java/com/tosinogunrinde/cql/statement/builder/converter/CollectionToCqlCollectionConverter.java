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

import com.google.common.base.Strings;
import com.tosinogunrinde.cql.statement.builder.util.ObjectValidator;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
abstract class CollectionToCqlCollectionConverter implements Converter<Field, String> {

    @Override
    public String convert(Field field) {
        ObjectValidator.validateNotNull(field);
        StringBuilder builder = new StringBuilder(getType()).append("<");
        Type[] types = getTypesFromType(field.getGenericType());
        convert(types[0], EntryType.KEY, false, builder);
        if (types.length > 1) {
            builder.append(", ");
            convert(types[1], EntryType.VALUE, false, builder);
        }
        builder.append(">");
        return builder.toString();
    }

    private Type[] getTypesFromType(Type type) {
        return ((ParameterizedType) type).getActualTypeArguments();
    }

    private void convert(Type type, EntryType entryType, boolean frozen, StringBuilder builder) {
        if (type instanceof Class) {
            String cqlType = new TypeToCqlTypeConverter().convert(type.getTypeName());
            if (!Strings.isNullOrEmpty(cqlType)) {
                builder.append(cqlType);
            } else if (!frozen) {  // Must be a UDT.
                builder.append(freezeField(type));
            } else {
                if (entryType == EntryType.VALUE) {
                    builder.append(", ");
                }
                builder.append(((Class) type).getSimpleName().toLowerCase());
            }
        } else {
            builder.append("frozen<").append(getCollectionType(type)).append("<");
            Type[] types = getTypesFromType(type);
            for (int index = 0; index < types.length; index++) {
                convert(types[index], index == 0 ? EntryType.KEY : EntryType.VALUE, true, builder);
            }
            builder.append(">>");
        }
    }

    private String freezeField(Type type) {
        return "frozen<" + ((Class) type).getSimpleName().toLowerCase() + ">";
    }

    private String getCollectionType(Type type) {
        Type rawType = ((ParameterizedType) type).getRawType();
        if (List.class.isAssignableFrom((Class<?>) rawType)) {
            return "list";
        } else if (Set.class.isAssignableFrom((Class<?>) rawType)) {
            return "set";
        } else if (Map.class.isAssignableFrom((Class<?>) rawType)) {
            return "map";
        }
        throw new UnsupportedOperationException("type is not supported: " + type);
    }

    protected abstract String getType();

    private enum EntryType {KEY, VALUE}
}