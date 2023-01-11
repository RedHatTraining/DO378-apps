package com.redhat.training.expense;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import java.util.List;

@Path( "/admin" )
public class AdminResource {

    @Inject
    ExpensesService expenses;

    @GET
    @Path( "/expenses" )
    @RolesAllowed({ "ADMIN" })
    public List<Expense> listAllExpenses() {
        return expenses.list();
    }

}