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

import com.tosinogunrinde.cql.statementbuilder.util.StringValidator;

/**
 * @author Tosin Ogunrinde
 */
public final class DataCenter {
    private final String name;
    private final int numberOfReplicas;

    public DataCenter(String name, int numberOfReplicas) {
        StringValidator.validateNotNullOrEmpty(name);
        if (numberOfReplicas < 1) {
            throw new IllegalArgumentException("numberOfReplicas must be greater than 0: " + numberOfReplicas);
        }
        this.name = name;
        this.numberOfReplicas = numberOfReplicas;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfReplicas() {
        return numberOfReplicas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof DataCenter)) {
            return false;
        }
        DataCenter dataCenter = (DataCenter) object;
        return dataCenter.getName().equals(getName());
    }
}