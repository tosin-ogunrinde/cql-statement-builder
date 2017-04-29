package com.tosinogunrinde.cql.statement.builder;

import com.datastax.driver.mapping.annotations.*;
import com.tosinogunrinde.cql.statement.builder.entitystatement.*;

import java.io.Serializable;

/**
 * @author Tosin Ogunrinde
 */
@Table(name = "organisations")
@SuppressWarnings("unused")
public class Organisation implements Serializable {
    private static final long serialVersionUID = 840884834091807002L;

    @PartitionKey
    private String id;

    @ClusteringColumn
    @ClusteringColumnOrder(clusteringOrder = ClusteringOrder.ASC)
    private String firstName;

    @ClusteringColumn(value = 1)
    @ClusteringColumnOrder(clusteringOrder = ClusteringOrder.DESC)
    private String lastName;

    @Transient
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}