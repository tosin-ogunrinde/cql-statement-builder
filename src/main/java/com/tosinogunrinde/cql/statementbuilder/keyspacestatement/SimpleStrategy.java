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

/**
 * @author Tosin Ogunrinde
 */
public final class SimpleStrategy extends ReplicationStrategy {
    private final int numberOfReplicas;

    public SimpleStrategy(String keyspaceName, int numberOfReplicas, boolean durableWrites) {
        super(keyspaceName, durableWrites);
        if (numberOfReplicas < 1) {
            throw new IllegalArgumentException("numberOfReplicas must be greater than 0: " + numberOfReplicas);
        }
        this.numberOfReplicas = numberOfReplicas;
    }

    public SimpleStrategy(String keyspaceName, int numberOfReplicas) {
        this(keyspaceName, numberOfReplicas, true);
    }

    public SimpleStrategy(String keyspaceName) {
        this(keyspaceName, 1);
    }

    public int getNumberOfReplicas() {
        return numberOfReplicas;
    }

    @Override
    protected String getProperty() {
        return "'replication_factor' : " + numberOfReplicas;
    }
}