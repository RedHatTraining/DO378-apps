package org.acme.conference.speaker;

import java.util.UUID;

import javax.json.bind.annotation.JsonbCreator;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Conference extends PanacheEntityBase{

    @Id
    @GeneratedValue
    public long conferenceId;

    public String conferenceUuid;
    public String conferenceName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Speaker speaker = new Speaker();

    public Integer noOfSpeakers;

    public Conference() {}

    public Conference(String conferenceName, Speaker speaker, Integer noOfSpeakers,
                     String conferenceUuid){
        this.conferenceName = conferenceName;
        this.speaker = speaker;
        this.noOfSpeakers = noOfSpeakers;
        this.conferenceUuid = conferenceUuid;
    }

    @JsonbCreator
    public Conference(String conferenceName, Speaker speaker, Integer noOfSpeakers) {
        this(conferenceName, speaker, noOfSpeakers, UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append("[");
      sb.append("<").append(conferenceId).append(">");
      sb.append(",");
      sb.append("nameFirst=").append(conferenceName);
      sb.append("]");
      return sb.toString();
  }

}
