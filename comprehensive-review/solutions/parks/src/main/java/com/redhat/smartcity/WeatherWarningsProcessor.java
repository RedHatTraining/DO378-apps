package com.redhat.smartcity;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import com.redhat.smartcity.weather.WeatherWarning;
import io.quarkus.logging.Log;

public class WeatherWarningsProcessor {

    @Inject
    ParkGuard guard;

    @Channel( "parks-under-warning" )
    Emitter<List<Park>> emitter;

    @Incoming( "weather-warnings" )
    @Transactional
    public CompletionStage<Void> processWeatherWarning( WeatherWarning warning ) {
        Log.info( "[EVENT Received] " + warning );

        List<Park> parks = Park.find( "city = ?1", warning.city ).list();

        for ( Park park : parks ) {
            guard.assessParkRisk( park, warning );
        }

        return emitter.send( parks );
    }

}
