package org.acme.conference.session;

import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SpeakerRepository implements PanacheRepository<Speaker> {
  
}
