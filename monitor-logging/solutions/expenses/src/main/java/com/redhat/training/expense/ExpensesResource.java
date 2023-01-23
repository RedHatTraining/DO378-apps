package com.redhat.training.expense;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;

import io.quarkus.logging.Log;

@Path( "/expenses" )
public class ExpensesResource {

    @Inject
    ExpensesRepository expenses;

    @GET
    @Path( "/{name}" )
    public Expense getByName( @PathParam( "name" ) String name ) {
        Log.debug( "Getting expense " + name );

        try {
            return expenses.getByName( name );
        } catch( ExpenseNotFoundException e ) {
            var message = e.getMessage();
            Log.error( message );
            throw new NotFoundException( message );
        }
    }

    @GET
    public List<Expense> getAll() {

        Log.error( "Expense does not exist" );
        Log.info( "Listing expenses" );
        Log.debug( "Calling expenses service" );
        return expenses.list();
    }

}
