package com.redhat.training.service;


import com.redhat.training.client.ExpenseServiceClient;
import com.redhat.training.model.Expense;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    @RestClient
    ExpenseServiceClient service;

    @GET
    public Set<Expense> getAll() {
        return service.getAll();
    }

    @POST
    public Expense create(Expense expense) {
        return service.create(expense);
    }
}
