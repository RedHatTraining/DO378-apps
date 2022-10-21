package com.redhat.training.client;

import com.redhat.training.model.Expense;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import java.util.Set;


public interface ExpenseServiceClient {

    @GET
    Set<Expense> getAll();

    @POST
    Expense create(Expense expense);
}
