package com.parking.rest;

import com.google.gson.Gson;
import com.parking.entity.Post;
import com.parking.rest.dto.UserTransfer;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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

//        assertEquals("admin", result.getName());
//        assertEquals(2, result.getRoles().size());

        Post post = new Post();
        post.setContent("test content");
        post.setTitle("test title");

        webResource = client().resource("http://localhost:8080/parking");
        String post1 = webResource.path("/com/parking/rest/blog-entries")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(post));

        System.out.println("post1 = " + post1);

        webResource = client().resource("http://localhost:8080/parking");
        post1 = webResource.path("/com/parking/rest/blog-entries")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("post1 = " + post1);

    }
}
