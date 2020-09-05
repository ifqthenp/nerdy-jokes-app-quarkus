package com.nerdyjokes;

import io.vertx.core.http.HttpServerRequest;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1")
public class NerdyJokeResource {

    @Context
    HttpServerRequest request;

    private static final Logger LOG = Logger.getLogger(NerdyJokeResource.class);

    @Inject
    @RestClient
    NerdyJokeResourceClient client;

    @Metered(
            name = "requests",
            unit = MetricUnits.SECONDS,
            description = "Rate of requests"
    )
    @Timed(
            name = "average_request",
            unit = MetricUnits.SECONDS,
            description = "Average duration of request"
    )
    @Counted(
            name = "number_of_requests",
            displayName = "Requests",
            description = "How many requests have been processed"
    )
    @GET
    @Path("/joke")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNerdyJoke(@DefaultValue("Johnny") @QueryParam("firstName") String firstName,
                                 @DefaultValue("Foobar") @QueryParam("lastName") String lastName) {

        LOG.infof("%s %s %s %s", request.method(), request.path(), request.params().entries(), request.remoteAddress().toString());

        return Response.ok(client.getRandomJoke("nerdy", firstName, lastName)).build();
    }
}