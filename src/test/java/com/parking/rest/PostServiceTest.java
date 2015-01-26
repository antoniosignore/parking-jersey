package com.parking.rest;

import com.google.gson.Gson;
import com.parking.rest.dto.UserTransfer;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class PostServiceTest extends ParkingTest {

	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}

	@Test
	public void testCreatePostSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("user", "user");

        WebResource webResource = client().resource("http://localhost:8080/");
		JSONObject json = webResource.path("/rest/user")
                .header("X-Auth-Token", authToken)
				.get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

		assertEquals("user", result.getName());
        assertEquals(1, result.getRoles().size());

	}


}
