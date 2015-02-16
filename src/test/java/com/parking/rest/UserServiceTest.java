package com.parking.rest;

import com.google.gson.Gson;
import com.parking.rest.dto.UserTransfer;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.net.URISyntaxException;

public class UserServiceTest extends ApplicationTest {


	@Test
	public void testUserFetchesSuccess() throws JSONException,
			URISyntaxException {

        String authToken = getToken("user", "user");

		WebResource webResource = client().resource("http://localhost:8080/parking");
		JSONObject json = webResource.path("/rest/accounts")
                .header("X-Auth-Token", authToken)
				.get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

        System.out.println("json = " + toPrettyFormat(json.toString()));

//		assertEquals("user", result.getName());
//        assertEquals(1, result.getRoles().size());

	}

//	@Test(expected = UniformInterfaceException.class)
//	public void testUserNotFound() throws JSONException, URISyntaxException {
//        String authToken = getToken("user", "user");
//        WebResource webResource = client().resource("http://localhost:8080/parking");
//        JSONObject json = webResource.path("/rest/account")
//                .header("X-Auth-Token", authToken)
//                .get(JSONObject.class);
//	}
}
