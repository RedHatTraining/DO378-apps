package com.redhat.smartcity.weather;

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

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setWarningType(WeatherWarningType type) {
        this.type = type;
    }

    public WeatherWarningType getWarningType() {
        return type;
    }

    public void setWarningLevel(WeatherWarningLevel level) {
        this.level = level;
    }

    public WeatherWarningLevel getWarningLevel() {
        return level;
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
