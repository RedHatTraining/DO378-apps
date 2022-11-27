package com.redhat.training.conference.session;


import java.util.Objects;
import java.util.UUID;

public class SpeakerFromService {
    String uuid;
    String nameFirst;
    String nameLast;

    SpeakerFromService() {
        this.uuid = UUID.randomUUID().toString();
    }

    SpeakerFromService(String uuid, String nameFirst, String nameLast) {
        this.uuid = uuid;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakerFromService that = (SpeakerFromService) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(nameFirst, that.nameFirst) && Objects.equals(nameLast, that.nameLast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nameFirst, nameLast);
    }

}