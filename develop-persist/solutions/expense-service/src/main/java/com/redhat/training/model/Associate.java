package com.redhat.training.model;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Associate extends PanacheEntity {
    public String name;

    @JsonbTransient
    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Expense> expenses = new ArrayList<>();

    public Associate() {
    }

    public Associate(String name) {
        this.name = name;
    }

    @JsonbCreator
    public static Associate of(String name) {
        return new Associate(name);
    }
}