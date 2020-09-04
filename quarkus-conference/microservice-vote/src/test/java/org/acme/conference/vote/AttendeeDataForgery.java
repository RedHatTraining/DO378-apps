package org.acme.conference.vote;

import java.util.UUID;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

public class AttendeeDataForgery {

    public final MongoClient mongoClient;

    public AttendeeDataForgery(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Attendee create (String uuid, String name) {
        MongoCollection collection = getCollection();
        Document doc = new Document().append("_id", uuid)
                .append("name", name);
        collection.insertOne(doc);
        Attendee attendee = new Attendee();
        attendee.setId(doc.getString("_id"));
        attendee.setName(doc.getString("name"));
        return attendee;
    }

    public Attendee createWithName (String name) {
        return create(UUID.randomUUID()
                .toString(), name);
    }

    public MongoCollection<Document> getCollection () {
        MongoCollection<Document> collection = this.mongoClient.getDatabase("votes")
                .getCollection("Attendee");
        return collection;
    }

    public void deleteAll () {
        getCollection().deleteMany(new Document());
    }
}
