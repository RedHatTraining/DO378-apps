package com.redhat.training;

import org.eclipse.microprofile.health.*;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@Readiness
@ApplicationScoped
public class DataHealthCheck implements HealthCheck {
    
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Adder Service")
                .up()
                //.withData("foo", "fooValue")
                .build();
    }
}
