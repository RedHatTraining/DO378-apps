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

@Path("/solver")
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
    public String solve(@PathParam("equation") String equation) {
        log.info("Solving '{}'", equation);
        String result;
        try {
            result = (Float.valueOf(equation)).toString();
        } catch (NumberFormatException e) {
            Matcher addMatcher = addPattern.matcher(equation);
            if (addMatcher.matches()) {
                try {
                    result = adderService.add(addMatcher.group(1), addMatcher.group(2));
                } catch (WebApplicationException ex) {
                    
                    throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Unable to add: "+equation+"\nReason: "+ex.getMessage()).build());
                }
            } else {
                Matcher multiplyMatcher = multiplyPattern.matcher(equation);
                if (multiplyMatcher.matches()) {
                    try {
                        result = multiplierService.multiply(multiplyMatcher.group(1), multiplyMatcher.group(2));
                    } catch (WebApplicationException ex) {
                        throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Unable to multiply: "+equation+"\nReason: "+ex.getMessage()).build());

                    }
                } else {
                    throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Unable to parse: "+equation).build());
                }
            }
        }
        return result;
    }
}

