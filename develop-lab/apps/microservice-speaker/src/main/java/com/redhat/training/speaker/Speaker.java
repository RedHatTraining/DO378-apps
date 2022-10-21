package com.redhat.training.speaker;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

public class Speaker {
    public String id = UUID.randomUUID().toString();
    public String name;
    public String organization;
}
