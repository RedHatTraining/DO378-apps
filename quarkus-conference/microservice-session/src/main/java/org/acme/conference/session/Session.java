package org.acme.conference.session;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

/**
 * Session entity
 * 
 */
@Entity
public class Session {

    @Id
    @NotBlank
    public String id;

    public int schedule;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Collection<Speaker> speakers = new HashSet<>();
  
}
