package com.redhat.training.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EndPointTest {

    @TestHTTPResource
    @TestHTTPEndpoint( ExpenseResource.class )
    URI expensesURI;

    @Test
    public void testEndpointPort() {
        assertEquals( 8081, expensesURI.getPort() );
    }

}