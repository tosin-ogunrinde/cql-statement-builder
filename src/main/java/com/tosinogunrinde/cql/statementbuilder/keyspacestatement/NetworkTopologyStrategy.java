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

import com.tosinogunrinde.cql.statementbuilder.util.CollectionValidator;

import java.util.Set;

/**
 * @author Tosin Ogunrinde
 */
public final class NetworkTopologyStrategy extends ReplicationStrategy {
    private final Set<DataCenter> dataCenters;

    public NetworkTopologyStrategy(String keyspaceName, Set<DataCenter> dataCenters, boolean durableWrites) {
        super(keyspaceName, durableWrites);
        CollectionValidator.validateNotNullOrEmpty(dataCenters);
        this.dataCenters = dataCenters;
    }

    public NetworkTopologyStrategy(String keyspaceName, Set<DataCenter> dataCenters) {
        this(keyspaceName, dataCenters, true);
    }

    @Override
    protected String getProperty() {
        StringBuilder builder = new StringBuilder();
        int iteration = 1;
        for (DataCenter dataCenter : dataCenters) {
            builder.append("'").append(dataCenter.getName()).append("'")
                    .append(" : ").append(dataCenter.getNumberOfReplicas());
            if (iteration != dataCenters.size()) {
                builder.append(", ");
            }
            iteration++;
        }
        return builder.toString();
    }

    public Set<DataCenter> getDataCenters() {
        return dataCenters;
    }
}