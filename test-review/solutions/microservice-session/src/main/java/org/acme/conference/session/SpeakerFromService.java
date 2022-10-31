package org.acme.conference.session;

import javax.json.bind.annotation.JsonbCreator;

public class SpeakerFromService {
    String uuid;
    String nameFirst;
    String nameLast;

    SpeakerFromService(String uuid, String nameFirst, String nameLast) {
        this.uuid = uuid;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    @JsonbCreator
    public static SpeakerFromService of(String uuid, String nameFirst, String nameLast) {
        return new SpeakerFromService(uuid, nameFirst, nameLast);
    }
}