package com.redhat.client;

import com.redhat.model.Expense;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import java.util.Set;


public interface ExpenseServiceClient {


    Set<Expense> getAll();


    Expense create(Expense expense);
}
