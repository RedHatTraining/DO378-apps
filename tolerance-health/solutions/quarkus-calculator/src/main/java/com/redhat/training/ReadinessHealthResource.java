package com.redhat.training;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessHealthResource implements HealthCheck {
    private int counter = 0;

    @Override
    public HealthCheckResponse call() {
        return counter++ > 10 ? HealthCheckResponse.up("Readiness") : HealthCheckResponse.down("Readiness");
    }
}
