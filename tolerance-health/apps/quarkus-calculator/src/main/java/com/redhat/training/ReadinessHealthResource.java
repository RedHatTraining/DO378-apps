package com.redhat.training;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class ReadinessHealthResource {

    private final String HEALTH_CHECK_NAME = "Readiness";
}
