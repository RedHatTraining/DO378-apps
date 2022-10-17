package com.redhat.smartcity.entities;

import java.time.LocalDateTime;


public class WeatherWarning {

    public String city;
    public WeatherWarningType type;
    public WeatherWarningLevel level;
    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public WeatherWarning() {
    }

    public WeatherWarning( String city, WeatherWarningType type, WeatherWarningLevel level, LocalDateTime startTime, LocalDateTime endTime ) {
        this.city = city;
        this.type = type;
        this.level = level;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "WeatherWarning{" +
                "city='" + city + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", start='" + startTime + '\'' +
                ", end='" + endTime + '\'' +
                ", level=" + level +
                '}';
    }
}
