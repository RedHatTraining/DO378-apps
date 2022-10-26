package com.redhat.training.rest;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.training.ExpenseService;
import com.redhat.training.model.Expense;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {

    @Inject
    ExpenseService service;

    @GET
    // TODO 1: Implement with a call to "listAll()" of Expense entity.
    // TODO 2: Add pagination and sort by "amount" and "associateId".
    public List<Expense> list() {
        return service.list();
    }

    @POST
    // TODO: Make the method transactional
    public Expense create(final Expense expense) {
        Expense newExpense = Expense.of(expense.name, expense.paymentMethod,
                expense.amount.toString(), expense.associateId);
        // TODO: Use the "persist()" method of the entity.
        service.create(newExpense);

        return newExpense;
    }

    @DELETE
    @Path("{uuid}")
    // TODO: Make the method transactional
    public List<Expense> delete(@PathParam("uuid") final UUID uuid) {
        // TODO: Use the "delete()" method of the entity.
        boolean deleted = service.delete(uuid);

        if (!deleted) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return service.list();
    }

    @PUT
    // TODO: Make the method transactional
    public void update(final Expense expense) {
        // TODO: Use the "update()" method of the entity.
        service.update(expense);
    }
}