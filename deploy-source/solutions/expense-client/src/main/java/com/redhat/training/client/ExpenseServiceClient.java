package com.redhat.training.client;

import com.redhat.training.model.Expense;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import java.util.Set;

@Path("/expenses")
@RegisterRestClient
public interface ExpenseServiceClient {

    @GET
    Set<Expense> getAll();

    @POST
    Expense create(Expense expense);
}
