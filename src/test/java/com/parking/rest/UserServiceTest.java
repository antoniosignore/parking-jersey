package com.parking.rest;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import com.google.gson.Gson;
import com.parking.transfer.UserTransfer;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.representation.Form;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.WebAppDescriptor;

import javax.ws.rs.core.MediaType;

public class UserServiceTest extends ParkingTest {
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}

    String token;

    public String getToken(String name, String password) throws JSONException,
            URISyntaxException {

        Form form = new Form();
        form.add("password", password);
        form.add("username", name);

        WebResource webResource = client().resource("http://localhost:8080");
        JSONObject json = webResource.path("/rest/user/authenticate")
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept("application/json")
                .type("application/json")
                .post(JSONObject.class,form);

        return (String) json.get("token");
    }

	@Test
	public void testUserFetchesSuccess() throws JSONException,
			URISyntaxException {
        String authToken = getToken("user", "user");
		WebResource webResource = client().resource("http://localhost:8080");
		JSONObject json = webResource.path("/rest/user")
                .header("X-Auth-Token", authToken)
				.get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

		assertEquals("user", result.getName());
        assertEquals(1, result.getRoles().size());

	}

	@Test(expected = ClientHandlerException.class)
	public void testUserNotFound() throws JSONException, URISyntaxException {
        String authToken = getToken("users", "user");
        WebResource webResource = client().resource("http://localhost:8080");
        JSONObject json = webResource.path("/rest/user")
                .header("X-Auth-Token", authToken)
                .get(JSONObject.class);
	}
}
