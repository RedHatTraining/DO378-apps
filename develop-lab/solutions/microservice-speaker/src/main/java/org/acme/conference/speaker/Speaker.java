package org.acme.conference.speaker;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Speaker
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "Speaker.getByOrganization", query = "from Speaker where organization = ?1")
})
public class Speaker extends PanacheEntity {

  public String uuid;
  public String nameFirst;
  public String nameLast;
  public String organization;

  @JsonbTransient
  public String biography;

  public String picture;
  public String twitterHandle;

  public Speaker() {}

  public Speaker(String nameFirst, String nameLast, String organization,
            String biography, String picture, String twitterHandle, String uuid) {
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.organization = organization;
        this.biography = biography;
        this.picture = picture;
        this.twitterHandle = twitterHandle;
        this.uuid = uuid;
  }

  public Speaker(String nameFirst, String nameLast, String organization,
            String biography, String picture, String twitterHandle) {
        this(nameFirst, nameLast, organization, biography, picture, twitterHandle,UUID.randomUUID().toString());
    }

  @JsonbCreator
    public Speaker(String nameFirst, String nameLast, String organization,
            String picture, String twitterHandle) {
        this(nameFirst, nameLast, organization, "Empty bio", picture, twitterHandle);
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