package com.redhat.training.cpu;

import java.util.List;

public class CpuStats {

    public List<Double> usageTimeSeries;
    public Double mean;
    public Double standardDeviation;

    public CpuStats(List<Double> usageTimeSeries, Double mean, Double standardDeviation) {
        this.usageTimeSeries = usageTimeSeries;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }
}
