package com.tosinogunrinde.cql.statementbuilder;

import com.datastax.driver.mapping.annotations.*;
import com.tosinogunrinde.cql.statementbuilder.entitystatement.*;

import java.io.Serializable;

/**
 * @author Tosin Ogunrinde
 */
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = -8845861228523962836L;

    @PartitionKey
    private String firstName;

    @PartitionKey(1)
    private String lastName;

    @ClusteringColumn
    @ClusteringColumnOrder(clusteringOrder = ClusteringOrder.ASC)
    private int age;

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