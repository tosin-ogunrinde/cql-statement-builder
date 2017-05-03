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
package com.tosinogunrinde.cql.statementbuilder;

import com.datastax.driver.mapping.annotations.*;
import com.tosinogunrinde.cql.statementbuilder.util.*;
import org.slf4j.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public abstract class TableStatementBuilder implements CqlStatementBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(TableStatementBuilder.class);
    private final Class entity;
    private final Object[] keyValues;
    private String statement;

    /**
     * Table-based CQL statement builder constructor.
     *
     * @param entity    The class annotated with {@link com.datastax.driver.mapping.annotations.Table}.
     *                  Note: the class must have at-least one key (i.e. {@link com.datastax.driver.mapping.annotations.PartitionKey}).
     *                  The {@link com.datastax.driver.mapping.annotations.ClusteringColumn}) is optional.
     * @param keyValues The values of the keys.
     *                  Note: the order of the values must match the order of their attributes in their class.
     */
    TableStatementBuilder(Class entity, Object... keyValues) {
        ObjectValidator.validateNotNull(entity);
        ArrayValidator.validateNotNullOrEmpty(keyValues);
        this.entity = entity;
        this.keyValues = keyValues;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void buildStatement() {
        List<String> keyNames = getKeyNames();
        String tableName = AnnotationUtils.getEntityName(entity);
        CollectionValidator.validateNotNullOrEmpty(keyNames);
        StringValidator.validateNotNullOrEmpty(tableName);

        StringBuilder builder = new StringBuilder(getCqlCommand()).append(" FROM ").append(tableName);
        builder.append(" WHERE ");
        for (int index = 0; index < keyValues.length; index++) {
            builder.append(keyNames.get(index)).append(" = ");
            if (keyValues[index] instanceof String || keyValues[index] instanceof Date) {
                builder.append(SingleQuoteEscaper.escapeDateOrStringValue(keyValues[index]));
            } else {
                builder.append(keyValues[index]);
            }
            if (index < keyValues.length - 1) {
                builder.append(" AND ");
            }
        }
        statement = builder.toString();
        LOG.debug(statement);
    }

    private List<String> getKeyNames() {
        List<String> keyNames = new ArrayList<>();
        for (Field field : entity.getDeclaredFields()) {
            if (field.getAnnotation(PartitionKey.class) != null || field.getAnnotation(ClusteringColumn.class) != null) {
                keyNames.add(AnnotationUtils.getFieldName(field));
            }
        }
        return keyNames;
    }

    protected abstract String getCqlCommand();
}