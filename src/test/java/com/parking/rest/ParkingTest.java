package com.parking.rest;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.test.framework.JerseyTest;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

public class ParkingTest extends JerseyTest {

    protected String token;

    protected String getToken(String name, String password) throws JSONException,
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


}
