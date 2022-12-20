package com.redhat.training.resource;

import com.redhat.training.event.HighRiskAccountDetected;
import com.redhat.training.event.LowRiskAccountDetected;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alerts")
public class AlertResource {

    private static final Logger LOGGER = Logger.getLogger(AlertResource.class.getName());

    @Inject @Channel("in-memory-low-risk-alerts")
    Publisher<LowRiskAccountDetected> lowRiskAlerts;

    @Inject @Channel("in-memory-high-risk-alerts")
    Publisher<HighRiskAccountDetected> highRiskAlerts;

    // Event processors ------------------------------------------------------------------------------------------------

    @Incoming("high-risk-alert-read")
    @Outgoing("in-memory-high-risk-alerts")
    @Broadcast
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public HighRiskAccountDetected processHighRiskEvent(HighRiskAccountDetected event) {
        return event;
    }

    @Incoming("low-risk-alert-read")
    @Outgoing("in-memory-low-risk-alerts")
    @Broadcast
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public LowRiskAccountDetected processLowRiskEvent(LowRiskAccountDetected event) {
        return event;
    }

    // Endpoints -------------------------------------------------------------------------------------------------------

    @GET
    @Path("risk/low")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<LowRiskAccountDetected> getLowRiskAlerts() {
        return lowRiskAlerts;
    }

    @GET
    @Path("risk/high")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<HighRiskAccountDetected> getHighRiskAlerts() {
        return highRiskAlerts;
    }
}
