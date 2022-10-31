package com.redhat.training.expense;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {

    @Inject
    @RestClient
    FraudScoreService fraudScoreService;

    @Inject
    public ExpenseService expenseService;

    @GET
    @Operation(summary = "Retrieves the list of expenses")
    @APIResponse(responseCode = "200")
    public List<Expense> list() {
        return expenseService.list();
    }

    @POST
    @Transactional
    @Operation(summary = "Adds an expense")
    @APIResponse(
            responseCode = "201",
            headers = {
                    @Header(
                            name = "uuid",
                            description = "ID of the created entity",
                            schema = @Schema(implementation = String.class)
                    ),
                    @Header(
                            name = "location",
                            description = "URI of the created entity",
                            schema = @Schema(implementation = String.class)
                    ),
            },
            description = "Entity successfully created"
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid entity"
    )
    public Response create(Expense expense, @Context UriInfo uriInfo) {

        if (!expenseService.meetsMinimumAmount(expense.amount)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Expense newExpense = expenseService.create(expense);

        return Response.created(generateUriForExpense(newExpense, uriInfo))
                .header("uuid", newExpense.uuid)
                .build();
    }

    @PUT
    @Transactional
    @Operation(summary = "Updates an expense")
    @APIResponse(
            responseCode = "200",
            description = "Entity successfully updated"
    )
    @APIResponse(
            responseCode = "404",
            description = "Entity not found"
    )
    public void update(Expense expense) {
        if (expenseService.exists(expense.uuid)) {
            expenseService.update(expense);
        } else {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{uuid}")
    @Transactional
    @Operation(summary = "Deletes an expense")
    @APIResponse(
            responseCode = "204",
            description = "Entity successfully deleted"
    )
    @APIResponse(
            responseCode = "404",
            description = "Entity not found"
    )
    public Response delete(@PathParam("uuid") UUID uuid) {
        expenseService.delete(uuid);

        return Response.noContent().build();
    }

    @POST
    @Path("/score")
    @Operation(summary = "Provides the fraud score from an external service")
    @APIResponse(
            responseCode = "200",
            description = "Entity successfully scored"
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid entity"
    )
    public Response fraudScore(Expense expense) {
        FraudScore fraud = fraudScoreService.getByAmount(expense.amount);

        if (fraud.score > 200) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }

    private URI generateUriForExpense(Expense expense, UriInfo uriInfo) {
        return uriInfo.getAbsolutePathBuilder().path("/{uuid}").build(expense.uuid);
    }
}