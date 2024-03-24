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
        String traceName = getConfigProperty( "quarkus.otel.service.name" );
        assertNotNull( traceName, "quarkus.otel.service.name must be set" );
    }

    @Test
    public void testJaegerSamplerType() {
        testConfigValueEquals( "quarkus.otel.traces.sampler", "traceidratio" );
    }

    @Test
    public void testJaegerSamplerParam() {
        testConfigValueEquals( "quarkus.otel.traces.sampler.arg", "1" );
    }

    @Test
    public void testJaegerSamplerEndpoint() {
        testConfigValueEquals( "quarkus.otel.exporter.otlp.traces.endpoint", "http://localhost:4317" );
    }

}
