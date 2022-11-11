package com.redhat.training.rest;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceConfigurableLifecycleManager;

public class PostgressDBTestResource implements QuarkusTestResourceConfigurableLifecycleManager<WithPostgresDB> {

    public static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>( "postgres:15" );

    private String name;
    private String username;
    private String password;

    @Override
    public void init( WithPostgresDB params ) {
        username = params.username();
        password = params.password();
        name = params.name();
    }

    @Override
    public Map<String, String> start() {
        DATABASE.withDatabaseName( name )
                .withUsername( username )
                .withPassword( password )
                .start();
        Map<String, String> datasourceProperties = new HashMap<>();
        datasourceProperties.put( "quarkus.datasource.username", username );
        datasourceProperties.put( "quarkus.datasource.password", password );
        datasourceProperties.put( "quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl() );
        return datasourceProperties;
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }

}
