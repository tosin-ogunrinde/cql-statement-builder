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
package com.tosinogunrinde.cql.statementbuilder.keyspacestatement;

import com.tosinogunrinde.cql.statementbuilder.CqlStatementBuilder;
import com.tosinogunrinde.cql.statementbuilder.util.ObjectValidator;
import org.slf4j.*;

/**
 * @author Tosin Ogunrinde
 */
public final class CreateKeyspaceStatementBuilder implements CqlStatementBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(CreateKeyspaceStatementBuilder.class);

    private final ReplicationStrategy replicationStrategy;
    private String statement;

    public CreateKeyspaceStatementBuilder(ReplicationStrategy replicationStrategy) {
        ObjectValidator.validateNotNull(replicationStrategy);
        this.replicationStrategy = replicationStrategy;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void buildStatement() {
        StringBuilder builder = new StringBuilder().append("CREATE KEYSPACE IF NOT EXISTS ").append(replicationStrategy.getKeyspaceName())
                .append(" WITH REPLICATION = {'class' : ")
                .append("'").append(replicationStrategy.getClass().getSimpleName()).append("', ")
                .append(replicationStrategy.getProperty());
        if (replicationStrategy.isDurableWrites()) {
            builder.append("};");
        } else {
            builder.append("} AND DURABLE_WRITES = false;");
        }
        statement = builder.toString();
        LOG.info(statement);
    }
}
