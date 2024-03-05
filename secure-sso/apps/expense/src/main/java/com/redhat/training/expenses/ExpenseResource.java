package com.redhat.training.expenses;

import java.util.List;
import java.util.UUID;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import io.quarkus.security.identity.SecurityIdentity;

@Path( "/expense" )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ExpenseResource {

    @Inject
    ExpenseValidator validator;

    @Inject
    SecurityIdentity identity;

    @GET
    @RolesAllowed( "read" )
    public List<Expense> list() {
        return Expense.listAll();
    }

    @POST
    @Transactional
    @RolesAllowed( "modify" )
    public Expense create( final Expense expense ) {
        Expense newExpense = Expense.of( expense.name, expense.paymentMethod, expense.amount.toString() );

        if ( !validator.isValid( newExpense ) ) {
            var response = Response
                    .status( Status.BAD_REQUEST )
                    .entity( "Expense is invalid. Verify expense amount" )
                    .build();
            throw new BadRequestException( response );
        }

        newExpense.persist();

        return newExpense;
    }

    @DELETE
    @Path( "{uuid}" )
    @Transactional
    @RolesAllowed( "delete" )
    public List<Expense> delete( @PathParam( "uuid" ) final UUID uuid ) {
        long numExpensesDeleted = Expense.delete( "uuid", uuid );

        if ( numExpensesDeleted == 0 ) {
            throw new WebApplicationException( Response.Status.NOT_FOUND );
        }

        return Expense.listAll();
    }

    @PUT
    @Transactional
    @RolesAllowed( "modify" )
    public void update( final Expense expense ) {
        if ( expense.uuid != null )
            Expense.update( expense );
        else
            throw new NotFoundException( "Expense id not provided." );
    }
}