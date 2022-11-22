package com.redhat.training.services;

import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Retry;


@ApplicationScoped
public class InfoService {

    private int callCount = 0;

    @Retry(maxRetries = 5)
    public Info getInfo() {
        callCount++;

        crashPossibly();

        return new Info();
    }

    private void crashPossibly() {
        if ( callCount % 5 > 0 ) {
            Log.error( "Request #" + callCount + " to InfoService has failed" );
            throw new RuntimeException( "InfoService failed due to unexpected error" ); 
        } else {
            Log.info( "Request #" + callCount + " to InfoService has succeeded" );
        }
    }

}
