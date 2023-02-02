package com.redhat.training;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.microprofile.config.ConfigProvider;

public class ConfigTestUtils {

    public static void testConfigValueEquals( String configName, String expectedValue ) {
        String value = getConfigProperty( configName );
        assertEquals( expectedValue, value, configName + " must be " + expectedValue );
    }

    public static String getConfigProperty( String name ) {
        String traceName = ConfigProvider.getConfig().getValue( name, String.class );
        return traceName;
    }

}
