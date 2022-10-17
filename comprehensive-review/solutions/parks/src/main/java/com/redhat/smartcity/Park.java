package com.redhat.smartcity;

import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Park extends PanacheEntity {

    enum Status {CLOSED, OPEN}

    public String name;
    public Integer size;
    public String city;
    public Status status;
}
