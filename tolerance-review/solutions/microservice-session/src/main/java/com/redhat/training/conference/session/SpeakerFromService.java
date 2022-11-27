package com.redhat.training.conference.session;


public class SpeakerFromService {
    String uuid;
    String nameFirst;
    String nameLast;

    SpeakerFromService(String uuid, String nameFirst, String nameLast) {
        this.uuid = uuid;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }
}