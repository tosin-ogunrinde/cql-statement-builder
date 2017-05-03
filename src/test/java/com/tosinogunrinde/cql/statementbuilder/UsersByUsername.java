package com.tosinogunrinde.cql.statementbuilder;

import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
@Table(name = UsersByUsername.TABLE)
@SuppressWarnings("unused")
public class UsersByUsername implements Serializable {
    public static final String TABLE = "users_by_username";
    private static final long serialVersionUID = -8845861228523962836L;

    @PartitionKey
    private String username;

    @Column(name = "organisation_id")
    private String organisationId;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.ORDINAL)
    private Country country;

    private Date createdDate;
    private List<String> locationIds;
    private Set<String> campaignIds;
    private Map<String, String> parents;

    @FrozenValue
    private Map<String, Address> addresses;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
    }

    public Set<String> getCampaignIds() {
        return campaignIds;
    }

    public void setCampaignIds(Set<String> campaignIds) {
        this.campaignIds = campaignIds;
    }

    public Map<String, String> getParents() {
        return parents;
    }

    public void setParents(Map<String, String> parents) {
        this.parents = parents;
    }

    public Map<String, Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Map<String, Address> addresses) {
        this.addresses = addresses;
    }
}