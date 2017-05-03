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
package com.tosinogunrinde.cql.statementbuilder.entitystatement;

import com.datastax.driver.mapping.annotations.Transient;
import com.tosinogunrinde.cql.statementbuilder.*;
import com.tosinogunrinde.cql.statementbuilder.converter.FieldToCqlColumnConverter;
import com.tosinogunrinde.cql.statementbuilder.util.*;
import org.slf4j.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
abstract class EntityStatementBuilder implements CqlStatementBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(EntityStatementBuilder.class);
    final Class entity;
    private String statement;

    EntityStatementBuilder(Class entity) {
        ObjectValidator.validateNotNull(entity);
        this.entity = entity;
    }

    @Override
    public void buildStatement() {
        String entityName = AnnotationUtils.getEntityName(entity);
        StringValidator.validateNotNullOrEmpty(entityName);
        StringBuilder builder = new StringBuilder("CREATE ").append(getEntityType().getCreateCommandIndicator()).append(" IF NOT EXISTS ");
        builder.append(entityName).append(" (");
        List<String> types = getDeclaredFieldsWithType();
        for (int index = 0; index < types.size(); index++) {
            builder.append(types.get(index));
            if (index != types.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(getPostfixStatement());
        statement = builder.toString();
        LOG.info(statement);
    }

    private List<String> getDeclaredFieldsWithType() {
        List<String> declaredFieldsWithType = new ArrayList<>();
        FieldToCqlColumnConverter fieldToCqlColumnConverter = new FieldToCqlColumnConverter();
        for (Field field : entity.getDeclaredFields()) {
            if (isDatabaseField(field)) {
                declaredFieldsWithType.add(AnnotationUtils.getFieldName(field) + " " + fieldToCqlColumnConverter.convert(field));
            }
        }
        return declaredFieldsWithType;
    }

    private boolean isDatabaseField(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && field.getAnnotation(Transient.class) == null;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return getStatement();
    }

    protected abstract EntityType getEntityType();

    protected abstract String getPostfixStatement();
}