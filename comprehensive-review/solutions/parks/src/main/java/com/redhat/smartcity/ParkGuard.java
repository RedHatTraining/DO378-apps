package com.redhat.smartcity;


import java.util.List;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.redhat.smartcity.weather.WeatherService;
import com.redhat.smartcity.weather.WeatherWarning;
import com.redhat.smartcity.weather.WeatherWarningLevel;
import com.redhat.smartcity.weather.WeatherWarningType;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class ParkGuard {

    @Inject
    @RestClient
    WeatherService weatherService;

    @Inject
    io.micrometer.core.instrument.MeterRegistry registry;

    private List<WeatherWarningLevel> unsafeLevels = Arrays.asList(
        WeatherWarningLevel.Orange,
        WeatherWarningLevel.Red
    );

    private List<WeatherWarningType> unsafeTypes = Arrays.asList(
        WeatherWarningType.Storm,
        WeatherWarningType.Rain,
        WeatherWarningType.Snow,
        WeatherWarningType.Wind
    );

    /**
     * Retrieve all weather alerts relevant for a park, based on the city,
     * and update the parks status if necessary
     *
     * @param park
     */
    @Fallback(fallbackMethod = "assumeNoWarnings")
    @Timeout
    public Uni<Void> checkWeatherForPark(Park park) {
        var warningsStream = weatherService.getWarningsByCity(park.city);

        return warningsStream
            .onItem()
                .invoke( warnings -> {
                        for (WeatherWarning warning : warnings) {
                            var parkClosed = updateParkBasedOnWarning(park, warning);
                            if (parkClosed) {
                                return;
                            }
                        }
                    })
                .replaceWithVoid();

    }

    public Uni<Void> assumeNoWarnings(Park park) {
        Log.warn(
            "Weather service is not reachable. " +
            "Assuming no weather warnings are active for park " +
            park.id + " (" + park.name + ")."
        );
        return Uni.createFrom().voidItem();
    }

    /**
     * Assess the severity of a single weather warning for a particular park,
     * and close the park if necessary
     * @param park
     * @param warning
     * @return true if the park is closed, false otherwise
     */
    public boolean updateParkBasedOnWarning(Park park, WeatherWarning warning) {
        if (mustCloseParkDueTo( warning )) {
            Log.info("Unsafe conditions in " + park.city + " due to " + warning.level + " weather warning (" + warning.type + ")");
            closePark(park);
            Log.info("Park " + park.id + " (" + park.name + ") has been closed");
            registry.counter("parksAffected.count").increment();
            return true;
        }

        openPark(park);
        return false;
    }

    private boolean mustCloseParkDueTo( WeatherWarning warning ) {
        return unsafeLevels.contains(warning.level) && unsafeTypes.contains(warning.type);
    }

    public void openPark(Park park) {
        park.status = Park.Status.OPEN;
        park.persist();
    }

    public void closePark(Park park) {
        park.status = Park.Status.CLOSED;
        park.persist();
    }
}
