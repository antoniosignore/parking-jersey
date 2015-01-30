package com.parking.rest.hateoas.asm;

import com.parking.core.services.util.ConnectionList;
import com.rest.mvc.ConnectionController;
import com.rest.resources.ConnectionListResource;
import com.rest.resources.ConnectionResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

public class ConnectionListResourceAsm extends ResourceAssemblerSupport<ConnectionList, ConnectionListResource> {


    public ConnectionListResourceAsm() {
        super(ConnectionController.class, ConnectionListResource.class);
    }

    @Override
    public ConnectionListResource toResource(ConnectionList connectionList) {
        List<ConnectionResource> resList = new ConnectionResourceAsm().toResources(connectionList.getConnections());
        ConnectionListResource finalRes = new ConnectionListResource();
        finalRes.setConnections(resList);
        return finalRes;
    }
}
