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
package com.tosinogunrinde.cql.statement.builder.keyspacestatement;

import com.tosinogunrinde.cql.statement.builder.util.StringValidator;
import org.slf4j.*;

/**
 * @author Tosin Ogunrinde
 */
public abstract class ReplicationStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(ReplicationStrategy.class);

    private final String keyspaceName;
    private final boolean durableWrites;

    public ReplicationStrategy(String keyspaceName, boolean durableWrites) {
        StringValidator.validateNotNullOrEmpty(keyspaceName);
        if (!durableWrites) {
            LOG.warn("Data written to " + keyspaceName + " keyspace bypasses the commit log. You risk losing data.");
        }
        this.keyspaceName = keyspaceName;
        this.durableWrites = durableWrites;
    }

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public boolean isDurableWrites() {
        return durableWrites;
    }

    protected abstract String getProperty();
}
