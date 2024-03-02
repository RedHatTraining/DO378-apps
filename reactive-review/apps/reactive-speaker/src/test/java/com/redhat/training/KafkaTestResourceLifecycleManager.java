package com.redhat.training;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

public class KafkaTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
    @Override
    public Map<String, String> start() {
        Map<String, String> env = new HashMap<>();

        Map<String, String> props1 = InMemoryConnector.switchIncomingChannelsToInMemory("new-speakers-in");
        Map<String, String> props2 = InMemoryConnector.switchOutgoingChannelsToInMemory("new-speakers-out");
        Map<String, String> props3 = InMemoryConnector.switchOutgoingChannelsToInMemory("employees-out");
        Map<String, String> props4 = InMemoryConnector.switchOutgoingChannelsToInMemory("upstream-members-out");

        env.putAll(props1);
        env.putAll(props2);
        env.putAll(props3);
        env.putAll(props4);

        return env;
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}
