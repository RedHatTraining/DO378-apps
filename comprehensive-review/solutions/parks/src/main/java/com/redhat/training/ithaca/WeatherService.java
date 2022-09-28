package com.redhat.training.ithaca;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@ApplicationScoped
public class WeatherService {
    private Map<String,String>  cityAlerts = Collections.synchronizedMap(new HashMap<>());
    private Set<WeatherWarning> warnings = Collections.newSetFromMap(Collections.synchronizedMap(new HashMap<>()));

    @PostConstruct
    void initData() {
        warnings.add(new WeatherWarning("San Francisco", WeatherWarningType.Wind, WeatherWarningLevel.Yellow, LocalDateTime.now(), LocalDateTime.now().plusHours(6)));
        warnings.add(new WeatherWarning("Paris", WeatherWarningType.Hurricane, WeatherWarningLevel.Orange, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(1)));
        warnings.add(new WeatherWarning("Barcelona", WeatherWarningType.Snow, WeatherWarningLevel.Yellow, LocalDateTime.now(), LocalDateTime.now().plusHours(14)));
        warnings.add(new WeatherWarning("Hong Kong", WeatherWarningType.Rain, WeatherWarningLevel.Yellow, LocalDateTime.now().plusHours(5), LocalDateTime.now().plusDays(2)));

    }


    public Map<String,String> getWeatherWarningsByCity(String city) {
         List<WeatherWarning> aList = warnings.stream().collect(Collectors.toList());
         for (WeatherWarning warning : aList) {
             if(warning.getCity().equals(city)) {
                 cityAlerts.put("City",city);
                 cityAlerts.put("Weather type", warning.getWarningType().toString());
                 cityAlerts.put("Weather level", warning.getWarningLevel().toString());
             }
         }
         return cityAlerts;
    }
}
