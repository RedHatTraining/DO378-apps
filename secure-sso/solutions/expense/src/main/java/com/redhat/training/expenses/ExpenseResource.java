package com.redhat.training.expenses;

import java.util.List;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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