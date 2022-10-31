package com.redhat.training.conference.speaker;

public class SpeakerFromService {
    String uuid;
    String nameFirst;
    String nameLast;

    SpeakerFromService(String uuid, String nameFirst, String nameLast) {
        this.uuid = uuid;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    public static SpeakerFromService of(String uuid, String nameFirst, String nameLast) {
        return new SpeakerFromService(uuid, nameFirst, nameLast);
    }
}