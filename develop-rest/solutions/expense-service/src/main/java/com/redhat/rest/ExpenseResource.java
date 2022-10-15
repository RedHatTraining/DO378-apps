package com.redhat.rest;

import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {
    @Inject
    public ExpenseService expenseService;

    @GET
    public Set<Expense> list() {
        return expenseService.list();
    }

    @POST
    public Expense create(Expense expense) {
        return expenseService.create(expense);
    }

    @DELETE
    @Path("{uuid}")
    public Set<Expense> delete(@PathParam("uuid") UUID uuid) {
        if (!expenseService.delete(uuid)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return expenseService.list();
    }

    @PUT
    public void update(Expense expense) {
        expenseService.update(expense);
    }
}