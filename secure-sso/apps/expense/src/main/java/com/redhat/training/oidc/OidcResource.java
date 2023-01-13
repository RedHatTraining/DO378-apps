package com.redhat.training.oidc;

import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import io.quarkus.security.identity.SecurityIdentity;

@Path( "/oidc" )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class OidcResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @NoCache
    @PermitAll
    public User me() {
        return new User(securityIdentity);
    }

    public static class User {

        private Set<String> roles;

        User(SecurityIdentity securityIdentity) {
            this.roles = securityIdentity.getRoles();
        }

        public Set<String> getRoles() {
            return this.roles;
        }
    }
}
