package com.redhat.training;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.training.service.AdderService;
import com.redhat.training.service.MultiplierService;
import com.redhat.training.service.SolverService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolverResource implements SolverService {
    final Logger log = LoggerFactory.getLogger(SolverResource.class);

    @Inject
    @RestClient
    AdderService adderService;

    @Inject
    @RestClient
    MultiplierService multiplierService;

    static final Pattern multiplyPattern = Pattern.compile("(.*)\\*(.*)");
    static final Pattern addPattern = Pattern.compile("(.*)\\+(.*)");

    @Override
    @GET
    @Path("{equation}")
    @Produces(MediaType.TEXT_PLAIN)
    public Float solve(@PathParam("equation") String equation) {
        log.info("Solving '{}'", equation);
        try {
            return Float.valueOf(equation);
        } catch (NumberFormatException e) {
            Matcher addMatcher = addPattern.matcher(equation);
            if (addMatcher.matches()) {
                return adderService.add(addMatcher.group(1), addMatcher.group(2));
            }
            Matcher multiplyMatcher = multiplyPattern.matcher(equation);
            if (multiplyMatcher.matches()) {
                return multiplierService.multiply(multiplyMatcher.group(1), multiplyMatcher.group(2));
            } else {
                throw new WebApplicationException(
                        Response.status(Response.Status.BAD_REQUEST).entity("Unable to parse: " + equation).build());
            }
        }
    }
}
