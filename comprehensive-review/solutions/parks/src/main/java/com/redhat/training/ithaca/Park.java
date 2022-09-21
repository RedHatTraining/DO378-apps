package com.redhat.training.ithaca;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Park extends PanacheEntity {

    public String name;
    public String city;

}
