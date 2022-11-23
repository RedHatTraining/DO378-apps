package com.redhat.training.sysinfo;

import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Retry;

/**
 * Simulate a microservice that reads sytem info from a cloud instance
 */
@ApplicationScoped
public class InfoService {

    private int callCount = 0;

    @Retry( maxRetries = 5 )
    public Info getInfo() {
        callCount++;

        crashPossibly();

        return new Info();
    }

    private void crashPossibly() {
        if ( callCount % 5 > 0 ) {
            Log.error( "Request #" + callCount + " has failed" );
            throw new RuntimeException( "InfoService failed due to unexpected error" );
        } else {
            Log.info( "Request #" + callCount + " has succeeded" );
        }
    }

}
