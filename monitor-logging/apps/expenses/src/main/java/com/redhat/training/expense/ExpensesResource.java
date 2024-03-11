package com.redhat.training.expense;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.NotFoundException;

import io.quarkus.logging.Log;

@Path( "/expenses" )
public class ExpensesResource {

    @Inject
    ExpensesRepository expenses;

    @GET
    @Path( "/{name}" )
    public Expense getByName( @PathParam( "name" ) String name ) {

        try {
            return expenses.getByName( name );
        } catch( ExpenseNotFoundException e ) {
            var message = e.getMessage();

            throw new NotFoundException( message );
        }
    }

    @GET
    public List<Expense> getAll() {
        return expenses.list();
    }

}
