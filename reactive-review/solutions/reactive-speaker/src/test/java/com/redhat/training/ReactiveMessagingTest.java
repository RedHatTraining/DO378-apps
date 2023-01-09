package com.redhat.training;

import com.redhat.training.event.EmployeeSignedUp;
import com.redhat.training.event.SpeakerWasCreated;
import com.redhat.training.event.UpstreamMemberSignedUp;
import com.redhat.training.model.Affiliation;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySink;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySource;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.*;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

import static com.redhat.training.SpeakerResourceTest.generateRequestBody;
import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReactiveMessagingTest {
    private final Long TESTING_ID = 1L;
    private final String TESTING_NAME = "A Person";
    private final String TESTING_EMAIL = "test@example.com";

    @Inject
    @Any
    InMemoryConnector connector;

    @BeforeAll
    public static void setup() {

    }

    @Test
    public void creatingASpeakerFiresAnEvent() {
        InMemorySink<SpeakerWasCreated> eventsOut = connector.sink("new-speakers-out");

        given()
            .body(generateRequestBody(Affiliation.NONE))
            .contentType("application/json")
        .when()
            .post("/speakers")
        .then()
            .statusCode(201);

        await().<List<? extends Message<SpeakerWasCreated>>>until(eventsOut::received, t -> t.size() == 1);

        SpeakerWasCreated queuedEvent = eventsOut.received().get(0).getPayload();

        Assertions.assertEquals(TESTING_ID, queuedEvent.id);
        Assertions.assertEquals(TESTING_NAME, queuedEvent.fullName);
        Assertions.assertEquals(TESTING_EMAIL, queuedEvent.email);
    }

    @Test
    public void givenANewRedHatSpeakerAnEventIsFired() {
        InMemorySource<SpeakerWasCreated> eventsIn = connector.source("new-speakers-in");
        InMemorySink<EmployeeSignedUp> eventsOut = connector.sink("employees-out");

        SpeakerWasCreated initialEvent = new SpeakerWasCreated(
                TESTING_ID,
                TESTING_NAME,
                Affiliation.RED_HAT,
                TESTING_EMAIL
        );

        eventsIn.send(initialEvent);

        await().<List<? extends Message<EmployeeSignedUp>>>until(eventsOut::received, t -> t.size() == 1);

        EmployeeSignedUp queuedEvent = eventsOut.received().get(0).getPayload();

        Assertions.assertEquals(TESTING_ID, queuedEvent.speakerId);
        Assertions.assertEquals(TESTING_NAME, queuedEvent.fullName);
        Assertions.assertEquals(TESTING_EMAIL, queuedEvent.email);
    }

    @Test
    public void givenAnUpstreamSpeakerAnEventIsFired() {
        InMemorySource<SpeakerWasCreated> eventsIn = connector.source("new-speakers-in");
        InMemorySink<UpstreamMemberSignedUp> eventsOut = connector.sink("upstream-members-out");

        SpeakerWasCreated initialEvent = new SpeakerWasCreated(
                TESTING_ID,
                TESTING_NAME,
                Affiliation.GNOME_FOUNDATION,
                TESTING_EMAIL
        );

        eventsIn.send(initialEvent);

        await().<List<? extends Message<UpstreamMemberSignedUp>>>until(eventsOut::received, t -> t.size() == 1);

        UpstreamMemberSignedUp queuedEvent = eventsOut.received().get(0).getPayload();

        Assertions.assertEquals(TESTING_ID, queuedEvent.speakerId);
        Assertions.assertEquals(TESTING_NAME, queuedEvent.fullName);
        Assertions.assertEquals(TESTING_EMAIL, queuedEvent.email);
    }
}
