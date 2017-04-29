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

import com.datastax.driver.mapping.annotations.*;
import com.tosinogunrinde.cql.statement.builder.*;
import com.tosinogunrinde.cql.statement.builder.util.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
class CreateTableStatementBuilder extends EntityStatementBuilder {

    CreateTableStatementBuilder(Class entity) {
        super(entity);
    }

    @Override
    protected EntityType getEntityType() {
        return EntityType.TABLE;
    }

    @Override
    protected String getPostfixStatement() {
        return ", " + getPartitionAndClusteringKeyStatement() + ")";
    }

    private String getPartitionAndClusteringKeyStatement() {
        List<Field> primaryKeys = AnnotationUtils.getFieldsAnnotatedWith(entity, Collections.singletonList(PartitionKey.class));
        List<Field> clusteringKeys = AnnotationUtils.getFieldsAnnotatedWith(entity, Collections.singletonList(ClusteringColumn.class));
        CollectionValidator.validateNotNullOrEmpty(primaryKeys, "At least one @PartitionKey must be specified");
        StringBuilder builder = new StringBuilder().append(new CqlStatementBuildDirector().buildStatement(new PartitionKeyStatementBuilder(primaryKeys)));
        if (!clusteringKeys.isEmpty()) {
            builder.append(", ").append(new CqlStatementBuildDirector().buildStatement(new ClusteringColumnStatementBuilder(clusteringKeys)));
        } else {
            builder.append(")");
        }
        return builder.toString();
    }
}