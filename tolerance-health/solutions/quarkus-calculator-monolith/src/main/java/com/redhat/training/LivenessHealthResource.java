package com.redhat.training;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class LivenessHealthResource implements HealthCheck {
    @Inject
    LivenessResource liveness;

    @Override
    public HealthCheckResponse call() {
        return liveness.isAlive() ? HealthCheckResponse.up("Liveness") : HealthCheckResponse.down("Liveness");
    }
}
