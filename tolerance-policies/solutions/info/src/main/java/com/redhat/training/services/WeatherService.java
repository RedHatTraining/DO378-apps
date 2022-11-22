package com.redhat.training.services;

import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;


@ApplicationScoped
public class WeatherService {

    private int callCount = 0;

    @Timeout( 200 )
    @Retry( maxRetries = 5, retryOn = TimeoutException.class )
    public String getConditions() {
        callCount++;

        delayPossibly();

        return "Sunny";
    }

    private void delayPossibly() {
        long start = System.currentTimeMillis();

        if ( callCount % 5 > 0 ) {
            Log.warn( "Request #" + callCount + " is taking too long..." );
            try {
                Thread.sleep( 5000 );
            } catch( InterruptedException e ) {
                Log.warnf( "Request #%d has been interrupted after %d seconds", callCount, System.currentTimeMillis() - start);
                return;
            }
        }

        Log.info( String.format( "Request #%d completed in %d seconds", callCount, System.currentTimeMillis() - start) );
    }
}
