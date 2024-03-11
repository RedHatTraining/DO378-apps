package com.redhat.training.client;

import com.redhat.training.model.Expense;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import java.util.Set;

@Path("/expenses")
@RegisterRestClient(configKey="expense")
public interface ExpenseServiceClient {

    @GET
    Set<Expense> getAll();

    @POST
    Expense create(Expense expense);
}
