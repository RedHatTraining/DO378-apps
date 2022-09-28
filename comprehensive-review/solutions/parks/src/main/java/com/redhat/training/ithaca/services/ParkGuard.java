package com.redhat.training.ithaca.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.training.ithaca.entities.Park;
import com.redhat.training.ithaca.entities.WeatherWarningLevel;
import com.redhat.training.ithaca.entities.WeatherWarningType;

@ApplicationScoped
public class ParkGuard {
    private Map<String,String>  weatherInfo = Collections.synchronizedMap(new HashMap<>());
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    WeatherService weatherService;

    public String checkWeather(Park park) {

    String status = "";
    String city = park.getCity();
    weatherInfo = weatherService.getWeatherWarningsByCity(city);
    String level = weatherInfo.get("Weather level");
    String type = weatherInfo.get("Weather type");

    if(level.equals(WeatherWarningLevel.Yellow.toString())) {
        logger.info("The weather level is Yellow, be attentive while going out.");
        status = Park.Status.OPEN.toString();
    } else if(level.equals(WeatherWarningLevel.Orange.toString())) {
        logger.info("The weather level is Orange, don't step out and be prepared");
        if(type.equals(WeatherWarningType.Hurricane.toString()) || type.equals(WeatherWarningType.Tornado.toString())
        || type.equals(WeatherWarningType.Storm.toString()) || type.equals(WeatherWarningType.DenseFog.toString())) {
            status = Park.Status.CLOSED.toString();
        } else {
            status = Park.Status.OPEN.toString();
        }
    } else {
        logger.info("The weather level is Red resulting into shut of entire City");
        status = Park.Status.CLOSED.toString();
    }

    return status;
    }
}
