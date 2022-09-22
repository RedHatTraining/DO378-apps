package com.redhat.training.ithaca;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.logging.Log;

@ApplicationScoped
public class WeatherWarningsProcessor {

    @Incoming("weather-warnings")
    public void processWarning(WeatherWarning warning) {
        Log.info("Received " + warning);
    }

}
