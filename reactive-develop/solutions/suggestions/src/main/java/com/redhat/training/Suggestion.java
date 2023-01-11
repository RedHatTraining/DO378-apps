package com.redhat.training;

import javax.persistence.Entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity
public class Suggestion extends PanacheEntity {
    public Long clientId;
    public Long itemId;

    public Suggestion() {
    }

    public Suggestion( Long clientId, Long itemId ) {
        this.clientId = clientId;
        this.itemId = itemId;
    }
}
