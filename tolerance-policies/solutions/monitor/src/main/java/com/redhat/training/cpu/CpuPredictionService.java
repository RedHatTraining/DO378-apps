package com.redhat.training.cpu;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import io.quarkus.logging.Log;

@ApplicationScoped
public class CpuPredictionService {

    private int callCount = 0;

    @CircuitBreaker( requestVolumeThreshold = 4 )
    public Double predictSystemLoad() {
        callCount++;

        crashPossibly();

        return Math.random();
    }

    private void crashPossibly() {
        if ( ( callCount - 1 ) % 4 > 1 ) {
            Log.error( "Prediction #" + callCount + " has failed" );
            throw new RuntimeException( "Prediction service not available due to high load" );
        } else {
            Log.info( "Prediction #" + callCount + " has succeeded" );
        }
    }
}
