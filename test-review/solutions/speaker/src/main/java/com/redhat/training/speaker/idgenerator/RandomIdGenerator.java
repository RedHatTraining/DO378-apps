package com.redhat.training.speaker.idgenerator;

import java.util.UUID;

import jakarta.inject.Singleton;

@Singleton
public class RandomIdGenerator implements IdGenerator {

    public String generate() {
        return UUID.randomUUID().toString();
    }

}
