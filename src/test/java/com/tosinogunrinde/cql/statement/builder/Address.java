package com.tosinogunrinde.cql.statement.builder;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;

/**
 * @author Tosin Ogunrinde
 */
@UDT(name = "address")
@SuppressWarnings("unused")
public class Address implements Serializable {
    private static final long serialVersionUID = 8871272578028585147L;

    @Field
    private String street;
    @Field
    private String city;
    @Field
    private int zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}