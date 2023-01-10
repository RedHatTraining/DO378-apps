package com.redhat.training;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

@Entity
public class Suggestion extends PanacheEntity {
    @NotNull
    public Long clientId;
    @NotNull
    public Long itemId;

    public Suggestion() {
    }

    public Suggestion( Long clientId, Long itemId ) {
        this.clientId = clientId;
        this.itemId = itemId;
    }
}
