package com.nerdyjokes;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/* Microprofile REST client */

@ApplicationScoped
@RegisterRestClient(baseUri = "https://api.icndb.com")
public interface NerdyJokeResourceClient {

    @GET
    @Path("/jokes/random")
    @Produces(MediaType.APPLICATION_JSON)
    JokeResponse getRandomJoke(@QueryParam("limitTo") String limit,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName);

}
