[![Build Status](https://travis-ci.org/tosin-ogunrinde/cql-statement-builder.svg?branch=master)](https://travis-ci.org/tosin-ogunrinde/cql-statement-builder)
[![Coverage Status](https://coveralls.io/repos/github/tosin-ogunrinde/cql-statement-builder/badge.svg?branch=master)](https://coveralls.io/github/tosin-ogunrinde/cql-statement-builder?branch=master)

# CQL Statement Builder

CQL Statement Builder is a Java library that automatically builds a range of [CQL]((http://cassandra.apache.org/doc/latest/cql/)) statements from classes/models. 
The statements generated can then be **executed** in a preferred library or framework. CQL Statement Builder can generate CQL statements for the following commands:

1. [CREATE KEYSPACE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlCreateKeyspace.html)
2. [CREATE TABLE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlCreateTable.html#cqlCreateTable)
3. [CREATE TYPE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlCreateType.html)
4. [DELETE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlDelete.html)
5. [DROP INDEX](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlDropIndex.html)
6. [DROP KEYSPACE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlDropKeyspace.html)
7. [DROP TABLE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlDropTable.html)
8. [DROP TYPE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlDropType.html)
9. [SELECT *](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlSelect.html)
10. [SELECT COUNT(*)](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlSelect.html)
11. [USE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/cqlUse.html)
 
CQL Statement Builder can also generate the CQL statements required to create or update schemas in an Apache Cassandra cluster. 
The schema generation feature is particularly useful because it will enable its user to focus on their code rather than worry about writing or re-writing CQL statements when a new class/model is created or when the structure of an old class/model changes.
CQL Statement Builder makes use of the standard annotations provided by the [Datastax Java Driver for Apache Cassandra](http://docs.datastax.com/en/developer/java-driver/2.1/), except for one:

@ClusteringColumnOrder: this annotation is used to indicate the order (i.e. Ascending or Descending) of the [@ClusteringColumn](http://docs.datastax.com/en/cql/3.1/cql/ddl/ddl_compound_keys_c.html). See User class example below.

## CREATE KEYSPACE
You can build CREATE KEYSPACE CQL statements with either [SimpleStrategy or NetworkTopologyStrategy](http://docs.datastax.com/en/cql/3.1/cql/cql_reference/create_keyspace_r.html) replication strategy.

### SimpleStrategy
The example below shows how to build a CREATE KEYSPACE CQL statement for a keyspace called foo with the replication_factor of 1.
```
new CqlStatementBuildDirector().buildStatement(new CreateKeyspaceStatementBuilder(new SimpleStrategy("foo", 1)));
```

### NetworkTopologyStrategy
The example below shows how to build a CREATE KEYSPACE CQL statement for a keyspace called foo with two data centers.
```
new CqlStatementBuildDirector().buildStatement(new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy("foo", getDataCenters())));
private Set<DataCenter> getDataCenters() {
    Set<DataCenter> dataCenters = new HashSet<>(2);
    dataCenters.add(new DataCenter("datacenter1", 2));
    dataCenters.add(new DataCenter("datacenter2", 3));
    return dataCenters;
}
```

## CREATE TABLE
The example below shows how to build a CREATE TABLE CQL statement from the User class. 

```
@Table(name = "user")
public class User {
  
    @PartitionKey
    private String username;
    
    @ClusteringColumn
    @ClusteringColumnOrder(clusteringOrder = ClusteringOrder.ASC)
    private String firstName;
    
    private String lastName;
    
    @Frozen
    private Address address;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
```
```
@UDT(name = "address")
public class Address {
    private String postCode;
    
    public String getPostCode() {
        return postCode;
    }
    
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
```
```
new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(User.class).getEntityStatementBuilder());
```

## CREATE TYPE
The example below shows how to build a CREATE TYPE CQL statement from the Address class above. 
```
new CqlStatementBuildDirector().buildStatement(new EntityStatementBuilderFactory(Address.class).getEntityStatementBuilder());
```

## DELETE
The example below shows how to build a DELETE CQL statement from the User class above. Multiple keys can also be specified if multiple partition keys 
or clustering columns are used. 
```
new CqlStatementBuildDirector().buildStatement(new DeleteStatementBuilder(User.class, "foo"));
```

## DROP
CQL Statement Builder can build the following DROP CQL statements:

1. DROP INDEX
2. DROP KEYSPACE
3. DROP TABLE
4. DROP TYPE

The example below shows how to build a DROP CQL statement for a KEYSPACE called foo.
```
new CqlStatementBuildDirector().buildStatement(new DropStatementBuilder(DroppableItem.KEYSPACE, "foo"));
```

## SELECT *
The example below shows how to build a SELECT * CQL statement from the User class above. 
Multiple keys can also be specified if multiple partition keys or clustering columns are used. 
```
new CqlStatementBuildDirector().buildStatement(new SelectStatementBuilder(User.class, "foo"));
```

## SELECT COUNT(*)
The example below shows how to build a SELECT COUNT(*) CQL statement from the User class above. 
Multiple keys can also be specified if multiple partition keys or clustering columns are used.
```
new CqlStatementBuildDirector().buildStatement(new CountStatementBuilder(User.class, "foo"));
```

## USE
The example below shows how to build a USE CQL statement for a keyspace called foo.

```
new CqlStatementBuildDirector().buildStatement(new UseStatementBuilder("foo"));
```

## SchemaStatementGenerator
The SchemaStatementGenerator generates the list of CREATE TABLE and CREATE TYPE CQL statements from classes annotated with UDT and Table annotations.
The example below shows how to generate the list of CQL statements where the classes are located in the "com.foo.bar" package. 
The list of statements can be used to create or update the schemas in an Apache Cassandra cluster.

```
new SchemaStatementGenerator("com.foo.bar").generateStatements();
```