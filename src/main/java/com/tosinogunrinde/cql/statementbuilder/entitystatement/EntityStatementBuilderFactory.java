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

import com.tosinogunrinde.cql.statementbuilder.EntityType;
import com.tosinogunrinde.cql.statementbuilder.util.*;

import java.util.Set;

/**
 * @author Tosin Ogunrinde
 */
class EntityStatementBuilderFactory {
    private final Class entity;

    EntityStatementBuilderFactory(Class entity) {
        ObjectValidator.validateNotNull(entity);
        this.entity = entity;
    }

    EntityStatementBuilder getEntityStatementBuilder() {
        if (getEntityType() == EntityType.TABLE) {
            return new CreateTableStatementBuilder(entity);
        } else {
            return new CreateUDTStatementBuilder(entity);
        }
    }

    private EntityType getEntityType() {
        Set<String> annotationTypes = AnnotationUtils.getClassAnnotationTypes(entity);
        if (annotationTypes.contains(EntityType.TABLE.getAnnotation())) {
            return EntityType.TABLE;
        } else if (annotationTypes.contains(EntityType.UDT.getAnnotation())) {
            return EntityType.UDT;
        } else {
            throw new UnsupportedOperationException("Only @Table and @UDT annotations are allowed for this operation");
        }
    }
}