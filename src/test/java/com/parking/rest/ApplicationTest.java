package com.parking.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.BeforeClass;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

public class ApplicationTest extends JerseyTest {

    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor
                .Builder("com.parking.rest.resources")
                .contextPath("/")
                .servletPath("api")
                .initParam("javax.ws.rs.Application", "com.parking.rest.application.ParkingApplication")
                .initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")
                .build();
    }

    @BeforeClass
    public static void registerParsers() {
        RestAssured.port = 9998;
        RestAssured.registerParser("application/json", Parser.JSON);
        RestAssured.registerParser("application/json", Parser.JSON);
        RestAssured.registerParser("application/json", Parser.JSON);
        RestAssured.registerParser("application/json", Parser.JSON);
        RestAssured.registerParser("application/json", Parser.JSON);
    }

    protected String getToken(String name, String password) throws JSONException, URISyntaxException {

        Form form = new Form();
        form.add("password", password);
        form.add("username", name);

        WebResource webResource = client().resource("http://localhost:8080/parking");
        JSONObject json = webResource.path("/rest/accounts/authenticate")
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept("application/json")
                .post(JSONObject.class, form);

        return (String) json.get("token");
    }


}
