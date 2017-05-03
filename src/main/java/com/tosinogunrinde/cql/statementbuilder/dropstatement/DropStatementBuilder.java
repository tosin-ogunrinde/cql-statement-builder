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
package com.tosinogunrinde.cql.statementbuilder.dropstatement;

import com.tosinogunrinde.cql.statementbuilder.CqlStatementBuilder;
import com.tosinogunrinde.cql.statementbuilder.util.*;
import org.slf4j.*;

/**
 * @author Tosin Ogunrinde
 */
public final class DropStatementBuilder implements CqlStatementBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(DropStatementBuilder.class);
    private final DroppableItem droppableItem;
    private final String itemName;
    private String statement;

    public DropStatementBuilder(DroppableItem droppableItem, String itemName) {
        ObjectValidator.validateNotNull(droppableItem);
        StringValidator.validateNotNullOrEmpty(itemName);
        this.droppableItem = droppableItem;
        this.itemName = itemName;
    }

    @Override
    public void buildStatement() {
        statement = "DROP " + droppableItem.name() + " IF EXISTS " + itemName;
        LOG.info(statement);
    }

    @Override
    public String getStatement() {
        return statement;
    }
}