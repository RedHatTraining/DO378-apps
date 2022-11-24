package com.redhat.training.status;

import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

/**
 * Simulate a microservice that reads the status of a cloud instance
 */
@ApplicationScoped
public class StatusService {

    private int callCount = 0;

    @Retry( maxRetries = 5, retryOn = TimeoutException.class )
    public String getStatus() {
        callCount++;

        delayPossibly();

        return "Running";
    }

    private void delayPossibly() {
        long start = System.currentTimeMillis();

        // Delay 4 out of 5 requests
        if ( callCount % 5 != 0 ) {
            Log.warn( "Request #" + callCount + " is taking too long..." );
            try {
                Thread.sleep( 5000 );
            } catch( InterruptedException e ) {
                Log.warnf( "Request #%d has been interrupted after %d milliseconds", callCount, System.currentTimeMillis() - start);
                return;
            }
        }

        Log.info( String.format( "Request #%d completed in %d milliseconds", callCount, System.currentTimeMillis() - start) );
    }
}
