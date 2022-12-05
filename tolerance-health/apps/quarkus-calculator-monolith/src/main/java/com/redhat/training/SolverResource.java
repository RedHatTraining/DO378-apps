package com.redhat.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.training.service.SolverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolverResource implements SolverService {
    final Logger log = LoggerFactory.getLogger(SolverResource.class);

    static final Pattern multiplyPattern = Pattern.compile("(.+)\\*(.+)");
    static final Pattern addPattern = Pattern.compile("(.+)\\+(.+)");

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
                return add(addMatcher.group(1),addMatcher.group(2));
            } 
            
            Matcher multiplyMatcher = multiplyPattern.matcher(equation);
            if (multiplyMatcher.matches()) {
                return multiply(multiplyMatcher.group(1),multiplyMatcher.group(2));
            }
            
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Unable to parse: "+equation).build());
        }
    }

    public Float add(String lhs, String rhs) {
        log.info("Adding {} to {}" ,lhs, rhs);
        return solve(lhs)+solve(rhs);
    }

    public Float multiply(String lhs, String rhs){
        log.info("Multiplying {} to {}" ,lhs, rhs);
        return solve(lhs)*solve(rhs);
    }
}
