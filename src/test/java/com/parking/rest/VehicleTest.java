package com.parking.rest;

import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;
import com.parking.entity.Vehicle;
import com.parking.rest.dto.UserTransfer;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;

public class VehicleTest extends ApplicationTest {

	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}

	@Test
	public void testCreateOneVehicleSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("admin", "admin");

        WebResource webResource = client().resource("http://localhost:8080/parking");
        JSONObject json = webResource.path("/accounts")
                .header("X-Auth-Token", authToken)
                .get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

        Assert.assertEquals("admin", result.getName());
        Assert.assertEquals(2, result.getRoles().size());

        Vehicle vehicle = new Vehicle();
        vehicle.setName("test content");
        vehicle.setLicensePlate("Q-123-QR");

        webResource = client().resource("http://localhost:8080/parking");
        String vehicle1 = webResource.path("/vehicles")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(vehicle));

        System.out.println("JSON \n" + toPrettyFormat(vehicle1));

        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/vehicles")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("GET root = " + toPrettyFormat(vehicle1));

    }

//    @Test
    public void testCreateTwoVehicleSuccess() throws JSONException, URISyntaxException {

        String authToken = getToken("admin", "admin");
        WebResource webResource = client().resource("http://localhost:8080/parking");
        JSONObject json = webResource.path("/accounts")
                .header("X-Auth-Token", authToken)
                .get(JSONObject.class);

        Gson gson = new Gson();
        UserTransfer result = gson.fromJson(json.toString(), UserTransfer.class);

        Assert.assertEquals("admin", result.getName());
        Assert.assertEquals(2, result.getRoles().size());

        Vehicle vehicle = new Vehicle();
        vehicle.setName("test content");
        vehicle.setLicensePlate("Q-111111-QR");

        webResource = client().resource("http://localhost:8080/parking");
        String vehicle1 = webResource.path("/vehicles")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(vehicle));

        System.out.println("JSON = \n" + toPrettyFormat(vehicle1));


        vehicle = new Vehicle();
        vehicle.setName("test content 2");
        vehicle.setLicensePlate("Q-222222-QR");

        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/vehicles")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .post(String.class, gson.toJson(vehicle));

        System.out.println("JSON = \n" + toPrettyFormat(vehicle1));

        // GET 1
        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/vehicles/33")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("GET root = " + toPrettyFormat(vehicle1));

        // GET ALL
        webResource = client().resource("http://localhost:8080/parking");
        vehicle1 = webResource.path("/vehicles")
                .header("X-Auth-Token", authToken)
                .accept("application/json")
                .type("application/json")
                .get(String.class);

        System.out.println("JSON = \n" + toPrettyFormat(vehicle1));

    }
}
