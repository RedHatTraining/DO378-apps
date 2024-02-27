package com.redhat.training.expense;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/score")
@ApplicationScoped
@RegisterRestClient
public interface FraudScoreService {
    @GET
    FraudScore getByAmount(@QueryParam("amount") double amount);
}
