package com.redhat.training;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.training.service.StateService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
public class LivenessHealthResource {

    private final String HEALTH_CHECK_NAME = "Liveness";

    @Inject
    StateService applicationState;
}
