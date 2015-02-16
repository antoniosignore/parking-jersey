package com.parking.rest;

import com.google.gson.Gson;
import com.parking.rest.dto.BlogEntryDto;
import com.parking.rest.dto.UserTransfer;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Date;

public class BlogEntriesTest extends ApplicationTest {


    @Test
    public void testCreatePostSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("admin", "admin");

        WebResource webResource = client().resource("http://localhost:8080/parking");
        JSONObject json = webResource.path("/rest/accounts")
                .header("X-Auth-Token", authToken)
                .get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

        Assert.assertEquals("admin", result.getName());
        Assert.assertEquals(2, result.getRoles().size());

        BlogEntryDto blogEntry = new BlogEntryDto();
        blogEntry.setContent("test content");
        blogEntry.setTitle("test title");
        String js = gson.toJson(blogEntry);

        webResource = client().resource("http://localhost:8080/parking");
        String post1 = webResource.path("/rest/blog-entries")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, js);

        System.out.println("JSON = \n" + toPrettyFormat(post1));


        webResource = client().resource("http://localhost:8080/parking");
        post1 = webResource.path("/rest/blog-entries")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("JSON = \n" + toPrettyFormat(post1));






    }
}
