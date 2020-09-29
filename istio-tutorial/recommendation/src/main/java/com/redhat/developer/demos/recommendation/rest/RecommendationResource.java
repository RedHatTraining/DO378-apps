package com.redhat.developer.demos.recommendation.rest;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Random;

import javax.json.Json;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

@Path("/")
public class RecommendationResource {

    private static final String RESPONSE_STRING_FORMAT = "recommendation v1 from '%s': %d\n";

    private static final String RESPONSE_STRING_FALLBACK_FORMAT = "recommendation fallback from '%s': %d\n";

    private final Logger logger = Logger.getLogger(getClass());

    /**
     * Counter to help us see the lifecycle
     */
    private int count = 0;

    /**
     * Flag for throwing a 503 when enabled
     */
    private boolean misbehave = false;
    private boolean timeout = false;
    private boolean crash = false;
    private Instant lastFlag;
    private static final String HOSTNAME = parseContainerIdFromHostname(System.getenv().getOrDefault("HOSTNAME", "unknown"));

    private long flagsThreshold = 10;

    static String parseContainerIdFromHostname(String hostname) {
        return hostname.replaceAll("recommendation-v\\d+-", "");
    }

    @GET
    // @Retry(maxRetries=5)
    @Timeout(500)
    @Fallback(fallbackMethod="getFallbackRecommendations")
    @CircuitBreaker(requestVolumeThreshold = 5, failureRatio = 0.4)
    public Response getRecommendations() {
        count++;
        logger.info(String.format("recommendation request from %s: %d", HOSTNAME, count));

        if (lastFlag!=null && lastFlag.isBefore(Instant.now().minus(Duration.ofSeconds(flagsThreshold)))){
            clearAllFlags();
        }

        if (timeout) {
            timeout();
        }

        if (crash) {
            crash();
        }

        logger.debug("recommendation service ready to return");
        if (misbehave) {
            return doMisbehavior();
        }
        return Response.ok(String.format(RESPONSE_STRING_FORMAT, HOSTNAME, count)).build();
    }

    public Response getFallbackRecommendations() {
        logger.info("recommendation service returning failback");
        return Response.ok(String.format(RESPONSE_STRING_FALLBACK_FORMAT, HOSTNAME, count)).build();
    }

    private void clearAllFlags(){
        logger.info("Clearing all flags");
        this.lastFlag=null;
        this.misbehave = false;
        this.timeout = false;
        this.crash = false;
    }

    private void timeout() {
        try {
            logger.info("This request will be delayed");
            Thread.sleep(new Random().nextInt(1_000));
        } catch (InterruptedException e) {
            logger.info("Thread interrupted");
        }
    }

    private Response doMisbehavior() {
        logger.info(String.format("Misbehaving %d", count));
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(String.format("recommendation misbehavior from '%s'\n", HOSTNAME)).build();
    }

    private void crash() {
        if (new Random().nextBoolean()) {
            logger.error("Crashing!");
            throw new RuntimeException("Resource failure.");
        }
    }

    @GET
    @Path("/misbehave")
    public Response flagMisbehave() {
        lastFlag = Instant.now();
        this.misbehave = !this.misbehave;
        logger.debug("'misbehave' has been set to " + misbehave);
        return Response.ok("Following requests to / will return a 503\n").build();
    }

    @GET
    @Path("/timeout")
    public Response flagTimeout() {
        lastFlag = Instant.now();
        this.timeout = !this.timeout;
        logger.debug("'timeout' has been set to " + timeout);
        return Response.ok("Following requests to / will return 200\n").build();
    }

    @GET
    @Path("/crash")
    public Response flagCrash() {
        lastFlag = Instant.now();
        this.crash = !this.crash;
        logger.debug("'crash' has been set to " + crash);
        return Response.ok("Following requests to / will return crash\n").build();
    }

}