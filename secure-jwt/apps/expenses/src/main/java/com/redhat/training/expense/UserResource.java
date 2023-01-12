package com.redhat.training.expense;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;

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