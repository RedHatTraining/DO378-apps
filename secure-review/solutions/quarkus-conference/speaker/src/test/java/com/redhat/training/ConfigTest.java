package com.redhat.training;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ConfigTest {

    private final Config props = ConfigProvider.getConfig();

    private final String OIDC_CONFIG_URL = props.getValue("quarkus.oidc.auth-server-url", String.class);
    private final String OIDC_URL = "https://localhost:9999/realms/quarkus";

    private final String OIDC_CONFIG_CLIENT_ID = props.getValue("quarkus.oidc.client-id", String.class);
    private final String OIDC_CLIENT_ID = "backend-service";

    private final String OIDC_CONFIG_CLIENT_PW = props.getValue("quarkus.oidc.credentials.secret", String.class);
    private final String OIDC_CLIENT_PW = "secret";


    @Test
    public void testOIDCConfiguration() {
        Assertions.assertEquals(OIDC_CONFIG_URL, OIDC_URL,
                        "OIDC server URL does not match the required URL");
        Assertions.assertEquals(OIDC_CONFIG_CLIENT_ID, OIDC_CLIENT_ID,
                "OIDC client ID does not match the required ID");
        Assertions.assertEquals(OIDC_CONFIG_CLIENT_PW, OIDC_CLIENT_PW,
                "OIDC client secret does not match the required secret");
    }

}
