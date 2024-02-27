package com.redhat.training.expense;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.SecurityContext;

@Path( "/user" )
public class UserResource {

    @Inject
    ExpensesService expenses;

    @GET
    @Path( "/expenses" )
    public List<Expense> listUserExpenses( SecurityContext context ) {
        var authenticatedUser = context.getUserPrincipal();

        if ( authenticatedUser == null ) {
            return new ArrayList<>();
        }

        return expenses.listByOwner( authenticatedUser.getName() );
    }

}