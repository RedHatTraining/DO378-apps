package com.redhat.training.ithaca.entities;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Park extends PanacheEntity {

    enum Status {
        OPEN, CLOSED,
    }

    public String uuid;
    public String name;
    public Integer size;
    public String city;
    @Enumerated(EnumType.STRING)
    public Status status;

    public Park() {}

    public Park(String uuid, String name, Integer size, String city, Status status) {
        this.uuid = uuid;
        this.name = name;
        this.size = size;
        this.city = city;
        this.status = status;
    }

    public Park(String name, Integer size, String city, Status status) {
        this(UUID.randomUUID().toString(), name, size, city, status);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
