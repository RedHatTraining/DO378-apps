package com.redhat.training.services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Info {
    public final String NAME = System.getProperty( "os.name" );
    public final String ARCH = System.getProperty( "os.arch" );
    public final String VERSION = System.getProperty( "os.version" );
}
