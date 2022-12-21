package com.redhat.training;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/suggestion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SuggestionResource {

    @DELETE
    public Uni<Long> deleteAll() {
        return Suggestion.deleteAll();
    }
}
