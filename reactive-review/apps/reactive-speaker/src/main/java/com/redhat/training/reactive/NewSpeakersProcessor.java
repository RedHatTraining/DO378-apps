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
