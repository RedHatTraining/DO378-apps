package com.redhat.training.speaker;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Speaker extends PanacheEntity {
    public String name;
    public String organization;
}
