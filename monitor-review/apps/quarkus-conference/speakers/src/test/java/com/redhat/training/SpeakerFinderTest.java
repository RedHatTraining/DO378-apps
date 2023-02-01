package com.redhat.training;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.microprofile.opentracing.Traced;
import org.junit.jupiter.api.Test;

public class SpeakerFinderTest {

    @Test
    public void testClassIsTraced() throws ClassNotFoundException {
        var cls = ClassLoader.getSystemClassLoader().loadClass( "com.redhat.training.SpeakerFinder" );
        var annotation = cls.getAnnotation(Traced.class);

        assertNotNull(annotation, "SpeakerFinder class must be annotated with @Traced");
    }

}
