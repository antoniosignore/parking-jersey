package com.parking.rest.resources;

import com.jayway.jaxrs.hateoas.core.HateoasResponse;
import com.parking.rest.dto.HomeDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class HomeResource {

    @GET
    @Produces("application/json")
    public Response root() {

        return HateoasResponse.ok(new HomeDto())
                .link(LinkableIds.ACCOUNTS_GROUP_LIST_ID, Rels.ACCOUNT_GROUPS)
                .link(LinkableIds.CONNECTIONS_LIST_ID, Rels.CONNECTIONS)
                .link(LinkableIds.PARKINGS_LIST_ID, Rels.PARKINGS)
                .link(LinkableIds.BLOG_ENTRIES_LIST_ID, Rels.BLOG_ENTRIES)
                .link(LinkableIds.VEHICLES_LIST_ID, Rels.VEHICLES).build();

//                .link(LinkableIds.SEARCH_ITEM_ID, Rels.SEARCH, "maradona").build();
    }
}
