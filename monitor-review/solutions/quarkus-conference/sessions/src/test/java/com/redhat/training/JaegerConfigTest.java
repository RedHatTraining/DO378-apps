package com.redhat.training;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static com.redhat.training.ConfigTestUtils.getConfigProperty;
import static com.redhat.training.ConfigTestUtils.testConfigValueEquals;

@QuarkusTest
public class JaegerConfigTest {

    @Test
    public void testJaegerServiceName() {
        String traceName = getConfigProperty( "quarkus.jaeger.service-name" );
        assertNotNull( traceName, "quarkus.jaeger.service-name must be set" );
    }

    @Test
    public void testJaegerSamplerType() {
        testConfigValueEquals( "quarkus.jaeger.sampler-type", "const" );
    }

    @Test
    public void testJaegerSamplerParam() {
        testConfigValueEquals( "quarkus.jaeger.sampler-param", "1" );
    }

    @Test
    public void testJaegerSamplerEndpoint() {
        testConfigValueEquals( "quarkus.jaeger.endpoint", "http://localhost:14268/api/traces" );
    }

    @Test
    public void testJaegerSamplerPropagation() {
        testConfigValueEquals( "quarkus.jaeger.propagation", "b3" );
    }

}
