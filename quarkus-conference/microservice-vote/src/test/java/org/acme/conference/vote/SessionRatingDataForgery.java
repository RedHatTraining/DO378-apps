package org.acme.conference.vote;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

public class SessionRatingDataForgery {

    public final MongoClient mongoClient;

    public SessionRatingDataForgery(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public SessionRating create (String session, String attendeeId, int rating) {
        Document doc = new Document().append("session", session)
                .append("attendeeId", attendeeId)
                .append("rating", rating)
                .append("ratingId", new ObjectId().toHexString());
        getCollection().insertOne(doc);
        SessionRating sessionRating = new SessionRating();
        sessionRating.setRatingId(doc.getString("ratingId"));
        sessionRating.setSession(doc.getString("session"));
        sessionRating.setAttendeeId(doc.getString("attendeeId"));
        sessionRating.setRating(doc.getInteger("rating"));
        sessionRating.id = doc.getObjectId("_id");
        return sessionRating;
    }

    public MongoCollection<Document> getCollection () {
        MongoCollection<Document> collection = this.mongoClient.getDatabase("votes")
                .getCollection("SessionRating");
        return collection;
    }

    public void deleteAll () {
        getCollection().deleteMany(new Document());
    }

}
