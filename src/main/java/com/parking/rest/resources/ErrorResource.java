package com.parking.rest.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/error")
public class ErrorResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Path("notfound")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notFound() {
        this.logger.info("Not Found!");
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("unauthorized")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response postUnauthorized() {
        return getUnauthorizedResponse();
    }

    @Path("unauthorized")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUnauthorized() {
        return getUnauthorizedResponse();
    }


    private Response getUnauthorizedResponse() {
        this.logger.info("Unauthorized!");
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


}
