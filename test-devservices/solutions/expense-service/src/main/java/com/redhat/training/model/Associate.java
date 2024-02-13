package com.redhat.training.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

// TODO: Add @Entity annotation and extend PanacheEntity
@Entity
public class Associate extends PanacheEntity {
    public String name;

    @JsonbTransient
    @OneToMany(mappedBy = "associate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // TODO: Add one to many relationship between associate and expenses
    public List<Expense> expenses = new ArrayList<>();

    // TODO: Add a default constructor
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
