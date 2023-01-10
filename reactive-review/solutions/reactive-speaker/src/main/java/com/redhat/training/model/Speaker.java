package com.redhat.training.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Speaker extends PanacheEntity {
    public String fullName;
    public Affiliation affiliation;

    public String email;
}
