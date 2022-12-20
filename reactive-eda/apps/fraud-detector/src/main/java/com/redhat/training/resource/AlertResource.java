package com.redhat.training.resource;

import com.redhat.training.event.HighRiskAccountWasDetected;
import com.redhat.training.event.LowRiskAccountWasDetected;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alerts")
public class AlertResource {

    @Inject @Channel("in-memory-low-risk-alerts")
    Publisher<LowRiskAccountWasDetected> lowRiskAlerts;

    @Inject @Channel("in-memory-high-risk-alerts")
    Publisher<HighRiskAccountWasDetected> highRiskAlerts;

    // Event processors ------------------------------------------------------------------------------------------------

    @Incoming("high-risk-alerts-in")
    @Outgoing("in-memory-high-risk-alerts")
    @Broadcast
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public HighRiskAccountWasDetected processHighRiskEvent(HighRiskAccountWasDetected event) {
        return event;
    }

    @Incoming("low-risk-alerts-in")
    @Outgoing("in-memory-low-risk-alerts")
    @Broadcast
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public LowRiskAccountWasDetected processLowRiskEvent(LowRiskAccountWasDetected event) {
        return event;
    }

    // Endpoints -------------------------------------------------------------------------------------------------------

    @GET
    @Path("risk/low")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<LowRiskAccountWasDetected> getLowRiskAlerts() {
        return lowRiskAlerts;
    }

    @GET
    @Path("risk/high")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<HighRiskAccountWasDetected> getHighRiskAlerts() {
        return highRiskAlerts;
    }
}
