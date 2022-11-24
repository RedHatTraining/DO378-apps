package com.redhat.training.cpu;

import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;


@ApplicationScoped
public class CpuPredictionService {

    private int callCount = 0;
    private long lastCallTime = 0;

    public Double predictSystemLoad() {
        callCount++;

        crashPossibly();

        return Math.random();
    }

    private void crashPossibly() {
        var currentTime = System.currentTimeMillis();
        var gapMillis = currentTime - lastCallTime;
        lastCallTime = currentTime;

        // Fail if the last request was made less than 2 seconds ago
        if ( gapMillis < 2000 ) {
            Log.errorf( "Prediction #%d has failed", callCount );
            throw new RuntimeException( "Prediction service not available due to high load" );
        } else {
            Log.infof( "Prediction #%d has succeeded", callCount );
        }

    }
}
