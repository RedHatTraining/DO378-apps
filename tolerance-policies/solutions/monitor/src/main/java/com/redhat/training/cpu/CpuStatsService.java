package com.redhat.training.cpu;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import io.quarkus.logging.Log;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Fallback;


/**
 * Simulate a microservice that reads CPU utilization data from a cloud instance
 */
@ApplicationScoped
public class CpuStatsService {

    private int callCount = 0;
    List<Double> series;

    @Fallback( fallbackMethod = "getCpuStatsWithMissingValues" )
    public CpuStats getCpuStats() {
        series = getCpuUsageTimeSeries();
        var mean = calculateAverage(series);
        var standardDeviation = calculateStandardDeviation(series);

        return new CpuStats(series, mean, standardDeviation);
    }

    public CpuStats getCpuStatsWithMissingValues() {
        return new CpuStats(series, 0.0, 0.0 );
    }

    private Double calculateAverage( List<Double> series ) {
        var sum = series.stream().reduce( Double::sum ).get();
        return sum / series.size();
    }

    private Double calculateStandardDeviation( List<Double> series ) {
        var average = calculateAverage( series );

        var deviations = series
                .stream()
                .map( x -> Math.pow( x - average, 2 ) )
                .reduce( Double::sum )
                .get();

        return Math.sqrt( deviations / series.size() );
    }

    private List<Double> getCpuUsageTimeSeries() {
        callCount++;

        var series = IntStream.range( 1, 10 )
                .mapToDouble( x -> getCpuUsageAtTimePoint( x ) )
                .boxed()
                .collect( Collectors.toList() );

        simulateMissingValues( series );

        return series;
    }

    private Double getCpuUsageAtTimePoint( int point ) {
        return Math.random();
    }

    private void simulateMissingValues( List<Double> series ) {
        if (callCount % 3 == 0) {
            series.set( 1, null );
            series.set( 3, null );
            series.set( 5, null );

            Log.warnf("Cpu usage data in request #%d contains null values", callCount);
        }
    }
}
