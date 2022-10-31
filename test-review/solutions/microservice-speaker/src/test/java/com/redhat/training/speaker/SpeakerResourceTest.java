package com.redhat.training.speaker;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint( SpeakerResource.class )
@PostgresDB( name = "testing", username = "developer", password = "developer")
public class SpeakerResourceTest {

    // In progress
}
