package com.nerdyjokes;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1")
public class NerdyJokeResource {

    @Inject
    @RestClient
    NerdyJokeResourceClient client;

    @GET
    @Path("/joke")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNerdyJoke(@DefaultValue("Johnny") @QueryParam("firstName") String firstName,
                                 @DefaultValue("Foobar") @QueryParam("lastName") String lastName) {

        return Response.ok(client.getRandomJoke("nerdy", firstName, lastName)).build();
    }
}