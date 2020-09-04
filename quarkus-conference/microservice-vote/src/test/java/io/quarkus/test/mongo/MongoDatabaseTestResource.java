package io.quarkus.test.mongo;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.jboss.logging.Logger;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongoCmdOptionsBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * @author jzuriaga
 *
 */
public class MongoDatabaseTestResource implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOGGER = Logger.getLogger(MongoDatabaseTestResource.class);

    private static final String CONNECTION_STRING_PROPERTY = "%test.quarkus.mongodb.connection-string";

    private static MongodExecutable MONGO;

    protected static String getConfiguredConnectionString () {
        return getProperty(CONNECTION_STRING_PROPERTY);
    }

    protected static String getProperty (String name) {
        return Optional.ofNullable(name)
                .map(System::getProperty)
                .map(String::trim)
                .filter(String::isEmpty)
                .orElse(null);
    }

    @Override
    public Map<String, String> start () {
        String uri = getConfiguredConnectionString();
        // This switch allow testing against a running mongo database.
        if (uri == null) {
            Version.Main version = Version.Main.V4_0;
            int port = 27018;
            try {
                LOGGER.infof("Starting Mongo %s on port %s", version, port);
                IMongodConfig config = new MongodConfigBuilder().version(version)
                        .cmdOptions(new MongoCmdOptionsBuilder().useNoJournal(false)
                                .build())
                        .net(new Net(port, Network.localhostIsIPv6()))
                        .build();
                MONGO = MongodStarter.getDefaultInstance()
                        .prepare(config);
                MONGO.start();
                uri = "mongodb://localhost:" + port;
            } catch (IOException e) {
                LOGGER.error("Unable to start MongoDB", e);
            }
        } else {
            LOGGER.infof("Using existing Mongo %s", uri);
        }
        return Collections.singletonMap(CONNECTION_STRING_PROPERTY, uri);
    }

    @Override
    public void stop () {
        if (MONGO != null) {
            try {
                MONGO.stop();
            } catch (Exception e) {
                LOGGER.error("Unable to stop MongoDB", e);
            }
        }
    }

}
