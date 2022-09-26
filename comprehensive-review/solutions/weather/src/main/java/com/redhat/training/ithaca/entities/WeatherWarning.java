package com.redhat.training.ithaca.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WeatherWarning {
    
    public String city;
    public WeatherWarningType type;
    public WeatherWarningLevel level;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    public LocalDateTime startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
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
