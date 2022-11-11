package com.redhat.training.expense;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/score")
@ApplicationScoped
@RegisterRestClient
public interface FraudScoreService {
    @GET
    FraudScore getByAmount(@QueryParam("amount") double amount);
}
