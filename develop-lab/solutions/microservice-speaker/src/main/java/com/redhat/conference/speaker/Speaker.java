package org.acme.conference.speaker;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Speaker extends PanacheEntityBase {

  private static final Conference Object = null;

  @Id
  @GeneratedValue
  public long id;

  public String uuid;
  public String nameFirst;
  public String nameLast;
  public String organization;
  @JsonbTransient
  public String biography;
  public String picture;
  public String twitterHandle;

  @JsonbTransient
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Conference conference;

  public Speaker() {}

  public Speaker(String nameFirst, String nameLast, String organization,
            String biography, String picture, String twitterHandle, Conference conference, String uuid) {
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.organization = organization;
        this.biography = biography;
        this.picture = picture;
        this.twitterHandle = twitterHandle;
        this.conference = conference;
        this.uuid = uuid;
  }

  @JsonbCreator
    public Speaker(String nameFirst, String nameLast, String organization,
            String picture, String twitterHandle) {
        this(nameFirst, nameLast, organization, "Empty bio", picture, twitterHandle, Object , UUID.randomUUID().toString());
  }

  @Override
  public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append("[");
      sb.append("<").append(id).append(">");
      sb.append(",");
      sb.append("nameFirst=").append(nameFirst);
      sb.append(",");
      sb.append("nameLast=").append(nameLast);
      sb.append("]");
      return sb.toString();
  }
}
