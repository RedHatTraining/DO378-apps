package com.redhat.training.reactive;

import com.redhat.training.event.EmployeeSignedUp;
import com.redhat.training.event.SpeakerWasCreated;
import com.redhat.training.event.UpstreamMemberSignedUp;
import com.redhat.training.model.Affiliation;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class NewSpeakersProcessor {
    private static final Logger LOGGER = Logger.getLogger(NewSpeakersProcessor.class);

    @Channel("employees-out")
    Emitter<EmployeeSignedUp> employeeEmitter;

    @Channel("upstream-members-out")
    Emitter<UpstreamMemberSignedUp> upstreamEmitter;

    @Incoming("new-speakers-in")
    public CompletionStage<Void> sendEventNotifications(
            Message<SpeakerWasCreated> message
    ) {
        SpeakerWasCreated event = message.getPayload();

        logProcessEvent(event.id);

        if (event.affiliation == Affiliation.RED_HAT) {
            logEmitEvent("EmployeeSignedUp", event.affiliation);
            employeeEmitter.send(
                    new EmployeeSignedUp(event.id, event.fullName, event.email)
            );
        } else if (event.affiliation == Affiliation.GNOME_FOUNDATION) {
            logEmitEvent("UpstreamMemberSignedUp", event.affiliation);
            upstreamEmitter.send(
                    new UpstreamMemberSignedUp(event.id, event.fullName, event.email)
            );
        }

        return message.ack();
    }

    private void logEmitEvent(String eventName, Affiliation affiliation) {
        LOGGER.infov(
                "Sending event {0} for affiliation {1}",
                eventName,
                affiliation
        );
    }

    private void logProcessEvent(Long eventID) {
        LOGGER.infov(
                "Processing SpeakerWasCreated event: ID {0}",
                eventID
        );
    }
}
