package org.acme.quickstart;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
// import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/")
@RegisterRestClient
public interface HelloService {

    @GET
    Uni<Person> get();
}