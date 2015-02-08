package com.parking.rest;

import com.google.gson.Gson;
import com.parking.entity.BlogEntry;
import com.parking.rest.dto.UserTransfer;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;

public class BlogEntriesTest extends ApplicationTest {


    @Test
    public void testCreatePostSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("admin", "admin");

        WebResource webResource = client().resource("http://localhost:8080/parking");
        JSONObject json = webResource.path("/com/parking/rest/accounts")
                .header("X-Auth-Token", authToken)
                .get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

        Assert.assertEquals("admin", result.getName());
        Assert.assertEquals(2, result.getRoles().size());

        BlogEntry blogEntry = new BlogEntry();
        blogEntry.setContent("test content");
        blogEntry.setTitle("test title");

        webResource = client().resource("http://localhost:8080/parking");
        String post1 = webResource.path("/com/parking/rest/blog-entries")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(blogEntry));

        System.out.println("JSON = \n" + toPrettyFormat(post1));


        webResource = client().resource("http://localhost:8080/parking");
        post1 = webResource.path("/com/parking/rest/blog-entries")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("JSON = \n" + toPrettyFormat(post1));






    }
}
