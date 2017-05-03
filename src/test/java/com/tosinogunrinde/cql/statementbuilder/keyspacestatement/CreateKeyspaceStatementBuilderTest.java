package com.tosinogunrinde.cql.statementbuilder.keyspacestatement;

import com.tosinogunrinde.cql.statementbuilder.CqlStatementBuildDirector;
import org.junit.*;

import java.util.*;

/**
 * @author Tosin Ogunrinde
 */
public class CreateKeyspaceStatementBuilderTest {
    private static final String KEYSPACE_NAME = "foobar";
    private static final String DATA_CENTER_ONE = "foo1";
    private static final String DATA_CENTER_TWO = "foo2";

    @Test(expected = IllegalArgumentException.class)
    public void testKeyspaceStatementBuilderWithNullReplicationStrategy() {
        new CreateKeyspaceStatementBuilder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimpleStrategyBuilderWithNullKeyspace() {
        new CreateKeyspaceStatementBuilder(new SimpleStrategy(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimpleStrategyBuilderWithInvalidNumberOfReplicas() {
        new CreateKeyspaceStatementBuilder(new SimpleStrategy(KEYSPACE_NAME, 0));
    }

    @Test
    public void testSimpleStrategyBuilder() {
        Assert.assertEquals("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1};",
                new CqlStatementBuildDirector().buildStatement(new CreateKeyspaceStatementBuilder(new SimpleStrategy(KEYSPACE_NAME))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNetworkTopologyStrategyBuilderWithNullKeyspace() {
        new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy(null, getDataCenters()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNetworkTopologyStrategyBuilderWithNullDataCenters() {
        new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy(KEYSPACE_NAME, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNetworkTopologyStrategyBuilderWithEmptyDataCenter() {
        new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy(KEYSPACE_NAME, new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNetworkTopologyStrategyBuilderWithInvalidNumberOfReplicas() {
        new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy(KEYSPACE_NAME, Collections.singleton(new DataCenter(DATA_CENTER_ONE, -1))));
    }

    @Test
    public void testNetworkTopologyStrategyBuilderWithDurableWrites() {
        Assert.assertEquals("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', " +
                        "'" + DATA_CENTER_ONE + "' : 2, '" + DATA_CENTER_TWO + "' : 3};",
                new CqlStatementBuildDirector().buildStatement(new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy(KEYSPACE_NAME, getDataCenters()))));
    }

    @Test
    public void testNetworkTopologyStrategyBuilderWithoutDurableWrites() {
        Assert.assertEquals("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', " +
                        "'" + DATA_CENTER_ONE + "' : 2, '" + DATA_CENTER_TWO + "' : 3} AND DURABLE_WRITES = false;",
                new CqlStatementBuildDirector().buildStatement(new CreateKeyspaceStatementBuilder(new NetworkTopologyStrategy(KEYSPACE_NAME, getDataCenters(), false))));
    }

    private Set<DataCenter> getDataCenters() {
        Set<DataCenter> dataCenters = new LinkedHashSet<>(2);
        dataCenters.add(new DataCenter(DATA_CENTER_ONE, 2));
        dataCenters.add(new DataCenter(DATA_CENTER_TWO, 3));
        return dataCenters;
    }
}