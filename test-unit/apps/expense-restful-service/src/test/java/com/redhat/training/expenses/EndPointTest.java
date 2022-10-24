package com.redhat.training.expenses;

import java.net.URI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndPointTest {

    URI expensesURI;

    @Test
    public void testEndpointPort() {
        assertEquals( 8081, expensesURI.getPort() );
    }

}