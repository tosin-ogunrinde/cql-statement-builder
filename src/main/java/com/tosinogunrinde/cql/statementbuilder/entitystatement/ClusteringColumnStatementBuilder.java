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

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Tosin Ogunrinde
 */
class ClusteringColumnStatementBuilder extends KeyStatementBuilder {
    private String statement;

    ClusteringColumnStatementBuilder(List<Field> fields) {
        super(fields);
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void buildStatement() {
        super.buildStatement();
        StringBuilder builder = new StringBuilder(super.getStatement())
                .append(")) WITH CLUSTERING ORDER BY (");
        for (int index = 0; index < fields.size(); index++) {
            ClusteringOrder clusteringOrder = fields.get(index).getAnnotation(ClusteringColumnOrder.class).clusteringOrder();
            builder.append(fields.get(index).getName())
                    .append(" ").append(clusteringOrder);
            if (index != fields.size() - 1) {
                builder.append(", ");
            }
        }
        statement = builder.toString();
    }

    @Override
    public String toString() {
        return getStatement();
    }
}