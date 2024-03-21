package com.redhat.training.conference.session;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SpeakerRepository implements PanacheRepository<Speaker> {
  
}
