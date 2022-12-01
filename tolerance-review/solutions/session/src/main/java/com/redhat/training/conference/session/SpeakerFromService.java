package com.redhat.training.conference.session;


import java.util.Objects;
import java.util.UUID;

public class SpeakerFromService {
    String uuid;
    String nameFirst;
    String nameLast;

    public SpeakerFromService() {
        this.uuid = UUID.randomUUID().toString();
    }

    public SpeakerFromService(String uuid, String nameFirst, String nameLast) {
        this.uuid = uuid;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakerFromService that = (SpeakerFromService) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(nameFirst, that.nameFirst) &&
                Objects.equals(nameLast, that.nameLast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nameFirst, nameLast);
    }

    @Override
    public String toString() {
        return "SpeakerFromService{" +
                "uuid='" + uuid + '\'' +
                ", nameFirst='" + nameFirst + '\'' +
                ", nameLast='" + nameLast + '\'' +
                '}';
    }
}
