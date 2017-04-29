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
package com.tosinogunrinde.cql.statement.builder.entitystatement;

import com.tosinogunrinde.cql.statement.builder.CqlStatementBuilder;
import com.tosinogunrinde.cql.statement.builder.util.CollectionValidator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Tosin Ogunrinde
 */
abstract class KeyStatementBuilder implements CqlStatementBuilder {
    final List<Field> fields;
    private String statement;

    KeyStatementBuilder(List<Field> fields) {
        CollectionValidator.validateNotNullOrEmpty(fields);
        this.fields = fields;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void buildStatement() {
        StringBuilder builder = new StringBuilder();
        if (this instanceof PartitionKeyStatementBuilder) {
            builder.append("PRIMARY KEY (");
            if (fields.size() > 1) {
                builder.append("(");
            }
        }
        for (int index = 0; index < fields.size(); index++) {
            builder.append(fields.get(index).getName());
            if (index != fields.size() - 1) {
                builder.append(", ");
            }
        }
        if (this instanceof PartitionKeyStatementBuilder && fields.size() > 1) {
            builder.append(")");
        }
        statement = builder.toString();
    }

    @Override
    public String toString() {
        return getStatement();
    }
}