package com.parking.rest;

import com.parking.rest.dto.AccountGroupDto;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import java.net.URISyntaxException;

public class AccountGroupTest extends ApplicationTest {

	@Test
	public void testCreateOneGroupSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("admin", "admin");

        AccountGroupDto accountgroup = new AccountGroupDto();
        accountgroup.setDescription("test content");
        accountgroup.setName("family");

        WebResource webResource = client().resource("http://localhost:8080/parking");
        String vehicle1 = webResource.path("/rest/accountGroups")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(accountgroup));

        System.out.println("JSON \n" + toPrettyFormat(vehicle1));

        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/rest/accountGroups")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("GET root = " + toPrettyFormat(vehicle1));

    }

    @Test
    public void testCreateTwoGroupsSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("admin", "admin");

        AccountGroupDto accountgroup = new AccountGroupDto();
        accountgroup.setDescription("test content");
        accountgroup.setName("family");

        WebResource webResource = client().resource("http://localhost:8080/parking");
        String vehicle1 = webResource.path("/rest/accountGroups")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(accountgroup));

        System.out.println("JSON = \n" + toPrettyFormat(vehicle1));

        accountgroup = new AccountGroupDto();
        accountgroup.setName("family");
        accountgroup.setDescription("test content");

        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/rest/accountGroups")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(accountgroup));

        System.out.println("JSON = \n" + toPrettyFormat(vehicle1));

        // GET 1
        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/rest/accountGroups/3")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("GET root = " + toPrettyFormat(vehicle1));

        // GET ALL
        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/rest/accountGroups")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("JSON = \n" + toPrettyFormat(vehicle1));
    }
}
