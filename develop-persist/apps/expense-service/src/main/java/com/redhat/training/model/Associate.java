package com.redhat.training.model;

import java.util.List;
import java.util.ArrayList;

import javax.json.bind.annotation.JsonbCreator;

// TODO: Add @Entity annotation and extend PanacheEntity
public class Associate {
    public String name;

    // TODO: Add one to many relationship between associate and expenses
    public List<Expense> expenses = new ArrayList<>();

    // TODO: Add a default constructor

    public Associate(String name) {
        this.name = name;
    }

    @JsonbCreator
    public static Associate of(String name) {
        return new Associate(name);
    }
}