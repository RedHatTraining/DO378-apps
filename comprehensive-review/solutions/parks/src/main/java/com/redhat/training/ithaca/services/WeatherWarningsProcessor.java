package com.redhat.training.ithaca.services;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.redhat.training.ithaca.entities.WeatherWarning;

import io.quarkus.logging.Log;

@ApplicationScoped
public class WeatherWarningsProcessor {

    @Incoming("weather-warnings")
    public void processWarning(WeatherWarning warning) {
        Log.info("Received " + warning);
    }

}
