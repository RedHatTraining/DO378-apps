package com.redhat.training;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.junit.jupiter.api.Test;

public class SpeakerFinderTest {

    @Test
    public void testClassIsTraced() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        var cls = ClassLoader.getSystemClassLoader().loadClass( "com.redhat.training.SpeakerFinder" );
        Method allMethod = cls.getDeclaredMethod( "all" );
        var annotation = allMethod.getAnnotation( WithSpan.class );

        assertNotNull( annotation, "SpeakerFinder.all method must be annotated with @WithSpan" );
    }

}
