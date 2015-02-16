package com.parking.rest;

import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import java.io.InputStream;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class HomeDocumentTest extends ApplicationTest {

    @Test
    public void testGetRoot() throws JSONException, URISyntaxException {

        InputStream rootResponse =
                        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200)).
                        expect().
                        statusCode(200).
                        body("links.rel", hasItems("account-groups", "connections", "parkings", "blog-entries", "vehicles")).
                        body("links[0].method", equalTo("GET")).
                        body("links[1].method", equalTo("GET")).
                        body("links[2].method", equalTo("GET")).
                                body("links[3].method", equalTo("GET")).
                                body("links[4].method", equalTo("GET")).
        when().get("/rest").asInputStream();


//        String authToken = getToken("admin", "admin");
//
//        WebResource webResource = client().resource("http://localhost:8080/parking/rest");
//        String json = webResource
//                .header("X-Auth-Token", authToken)
//                .accept("application/json")
//                .type("application/json")
//                .get(String.class);
//
//        System.out.println("JSON = \n" + toPrettyFormat(json));

    }
}
