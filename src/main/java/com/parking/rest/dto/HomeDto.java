package com.parking.rest.dto;

import com.jayway.jaxrs.hateoas.HateoasLinkBean;

import java.util.Collection;
import java.util.Map;

public class HomeDto implements HateoasLinkBean {

    private Collection<Map<String, Object>> links;

    @Override
    public Collection<Map<String, Object>> getLinks() {
        return this.links;
    }

    @Override
    public void setLinks(Collection<Map<String, Object>> links) {
        this.links = links;
    }
}
