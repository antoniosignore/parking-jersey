package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.parking.rest.hateoas.RootDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RootResource {

    @GET
    @Produces("application/json")
    public Response root() {
        return HateoasResponse.ok(new RootDto())
                .link(LinkableIds.ACCOUNTS_GROUP_LIST_ID, Rels.ACCOUNT_GROUPS)
                .link(LinkableIds.CONNECTIONS_GROUP_LIST_ID, Rels.CONNECTIONS)
                .link(LinkableIds.PARKINGS_LIST_ID, Rels.PARKINGS)
                .link(LinkableIds.POSTS_LIST_ID, Rels.POSTS)
                .link(LinkableIds.VEHICLES_LIST_ID, Rels.VEHICLES)
                .link(LinkableIds.SEARCH_ITEM_ID, Rels.SEARCH, "foo").build();
    }
}
