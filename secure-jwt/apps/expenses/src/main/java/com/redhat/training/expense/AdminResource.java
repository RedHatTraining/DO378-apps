package com.redhat.training.expense;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path( "/admin" )
public class AdminResource {

    @Inject
    ExpensesService expenses;

    @GET
    @Path( "/expenses" )
    public List<Expense> listAllExpenses() {
        return expenses.list();
    }

}