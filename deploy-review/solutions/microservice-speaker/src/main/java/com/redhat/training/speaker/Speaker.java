package com.redhat.training.speaker;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;

@Entity
public class Speaker extends PanacheEntity {
    public String name;
    public String organization;
}
