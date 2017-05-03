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

import com.datastax.driver.mapping.annotations.*;
import com.google.common.base.Strings;
import com.tosinogunrinde.cql.statementbuilder.CqlStatementBuildDirector;
import com.tosinogunrinde.cql.statementbuilder.converter.TypeToCqlTypeConverter;
import com.tosinogunrinde.cql.statementbuilder.util.*;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public final class SchemaStatementGenerator {
    private final String basePackage;

    /**
     * @param basePackage the base package where the annotated {@link Table} and {@link UDT} are located.
     */
    public SchemaStatementGenerator(String basePackage) {
        StringValidator.validateNotNullOrEmpty(basePackage);
        this.basePackage = basePackage;
    }

    /**
     * Generates the CQL statements required to setup the application schema.
     *
     * @return the CQL statements.
     */
    public List<String> generateStatements() {
        List<String> statements = new ArrayList<>();
        Set<Class<?>> tableEntities = getClassesAnnotatedWith(Table.class);
        CollectionValidator.validateNotNullOrEmpty(tableEntities, "@Table entity not found in basePackage");
        generateStatementsForEntities(tableEntities, statements, new HashSet<>());
        return statements;
    }

    private Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> classTypeAnnotation) {
        return new Reflections(basePackage).getTypesAnnotatedWith(classTypeAnnotation);
    }

    private void generateStatementsForEntities(Set<Class<?>> entities, List<String> statements, Set<String> processedEntities) {
        for (Class entity : entities) {
            for (Field field : getFieldsAnnotatedWithFrozen(entity)) {
                if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                    generateStatementsForEntityCollectionTypes(getCollectionTypes(field.getGenericType()), statements, processedEntities);
                } else if (field.getAnnotation(Frozen.class) != null) {
                    generateStatementsForEntities(Collections.singleton(field.getType()), statements, processedEntities);
                }
            }
            addEntityStatement(entity, statements, processedEntities);
        }
    }

    private void generateStatementsForEntityCollectionTypes(Type[] types, List<String> statements, Set<String> processedEntities) {
        for (Type type : types) {
            generateStatementsForEntityType(type, statements, processedEntities);
        }
    }

    private void generateStatementsForEntityFields(List<Field> fields, List<String> statements, Set<String> processedEntities) {
        for (Field field : fields) {
            generateStatementsForEntityType(field.getGenericType(), statements, processedEntities);
        }
    }

    private Type[] getCollectionTypes(Type type) {
        return ((ParameterizedType) type).getActualTypeArguments();
    }

    private void generateStatementsForEntityType(Type type, List<String> statements, Set<String> processedEntities) {
        if (type instanceof Class) {
            String cql = new TypeToCqlTypeConverter().convert(type.getTypeName());
            if (Strings.isNullOrEmpty(cql) && !processedEntities.contains(type.getTypeName())) {
                generateStatementsForEntityFields(getFieldsAnnotatedWithFrozen((Class) type), statements, processedEntities);
                addEntityStatement((Class) type, statements, processedEntities);
            }
        } else {
            generateStatementsForEntityCollectionTypes(getCollectionTypes(type), statements, processedEntities);
        }
    }

    private List<Field> getFieldsAnnotatedWithFrozen(Class entity) {
        List<Class<? extends Annotation>> annotations = new ArrayList<>();
        annotations.add(Frozen.class);
        annotations.add(FrozenValue.class);
        return AnnotationUtils.getFieldsAnnotatedWith(entity, annotations);
    }

    private void addEntityStatement(Class entity, List<String> statements, Set<String> processedEntities) {
        statements.add(new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(entity).getEntityStatementBuilder()));
        processedEntities.add(entity.getTypeName());
    }
}