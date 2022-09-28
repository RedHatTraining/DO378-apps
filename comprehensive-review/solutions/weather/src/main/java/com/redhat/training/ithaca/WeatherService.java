package com.redhat.training.ithaca;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.redhat.training.ithaca.entities.WeatherWarning;
import com.redhat.training.ithaca.entities.WeatherWarningLevel;
import com.redhat.training.ithaca.entities.WeatherWarningType;


@ApplicationScoped
public class WeatherService {

    private Set<WeatherWarning> warnings = Collections.newSetFromMap(Collections.synchronizedMap(new HashMap<>()));

    @PostConstruct
    void initData() {
        warnings.add(new WeatherWarning("San Francisco", WeatherWarningType.Wind, WeatherWarningLevel.Yellow, LocalDateTime.now(), LocalDateTime.now().plusHours(6)));
        warnings.add(new WeatherWarning("Paris", WeatherWarningType.Hurricane, WeatherWarningLevel.Orange, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(1)));
        warnings.add(new WeatherWarning("London", WeatherWarningType.Snow, WeatherWarningLevel.Red, LocalDateTime.now(), LocalDateTime.now().plusHours(14)));
        warnings.add(new WeatherWarning("Hong Kong", WeatherWarningType.Rain, WeatherWarningLevel.Yellow, LocalDateTime.now().plusHours(5), LocalDateTime.now().plusDays(2)));
    }

    public Set<WeatherWarning> listAll() {
        return warnings;
    }

    public WeatherWarning create(WeatherWarning warning) {
        warnings.add(warning);
        return warning;
    }
}
