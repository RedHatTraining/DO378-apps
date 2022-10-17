package com.redhat.smartcity;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.redhat.smartcity.entities.WeatherWarning;
import com.redhat.smartcity.entities.WeatherWarningType;
import com.redhat.smartcity.entities.WeatherWarningLevel;

public class WeatherSimulation {

    public static List<WeatherWarning> run() {
        List<WeatherWarning> warnings = new ArrayList<>();

        int numAlerts = (int) (Math.random() * 5);

        for ( int i = 0; i <= numAlerts; i++ ) {
            warnings.add( new WeatherWarning(
                    pickRandomCity(),
                    pickRandomType(),
                    pickRandomLevel(),
                    LocalDateTime.now(),
                    LocalDateTime.now().plus( 1, ChronoUnit.DAYS ) ) );
        }

        return warnings;
    }

    private static WeatherWarningLevel pickRandomLevel() {
        var values = WeatherWarningLevel.values();
        int idx = (int) (Math.random() * values.length);

        return values[idx];
    }

    private static WeatherWarningType pickRandomType() {
        var values = WeatherWarningType.values();
        int idx = (int) (Math.random() * values.length);

        return values[idx];
    }

    private static String pickRandomCity() {
        String[] cities = {
            "Amsterdam",
            "Barcelona",
            "Brussels",
            "São Paulo"
        };

        int idx = (int) (Math.random() * cities.length);

        return cities[idx];
    }

}
