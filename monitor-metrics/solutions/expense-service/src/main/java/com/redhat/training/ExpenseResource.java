package com.redhat.training;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.apache.commons.lang3.time.StopWatch;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {

    private final StopWatch stopWatch = StopWatch.createStarted();

    @Inject
    public MeterRegistry registry;

    @Inject
    public ExpenseService expenseService;

    @PostConstruct
    public void initMeters() {
        registry.gauge(
            "timeSinceLastGetExpenses",
            Tags.of("description", "Time since the last call to GET /expenses"),
            stopWatch,
            StopWatch::getTime
        );
    }

    @POST
    public Expense create(Expense expense) {
        registry.counter("callsToPostExpenses").increment();

        return registry.timer("expenseCreationTime")
        .wrap(
            (Supplier<Expense>) () -> expenseService.create(expense)
            ).get();
    }

    @GET
    @Counted(value = "callsToGetExpenses")
    public Set<Expense> list() {
        stopWatch.reset();
        stopWatch.start();

        return expenseService.list();
    }

    @DELETE
    @Path("{uuid}")
    public Set<Expense> delete(@PathParam("uuid") UUID uuid) {
        if (!expenseService.delete(uuid)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return expenseService.list();
    }

    @PUT
    public void update(Expense expense) {
        expenseService.update(expense);
    }
}
