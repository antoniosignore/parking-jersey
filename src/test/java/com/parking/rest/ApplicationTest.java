package com.parking.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.parsing.Parser;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.BeforeClass;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.authentication.FormAuthConfig.springSecurity;

public class ApplicationTest extends JerseyTest {

    protected Gson gson = new Gson();

    public static String toPrettyFormat(String jsonString) {
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
                .servletPath("rest")
                .initParam("javax.ws.rs.Application", "com.parking.rest.application.ParkingApplication")
                .initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")
                .contextParam("contextConfigLocation", "classpath:context.xml")
                .contextListenerClass(org.springframework.web.context.ContextLoaderListener.class)
                .servletClass(SpringServlet.class).initParam("load-on-startup", "1")
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


        given().filter(ResponseLoggingFilter.logResponseIfStatusCodeIs(200)).
                auth().form(name, password, springSecurity()).
                when().
                urlEncodingEnabled(true).
                post("/rest/accounts/authenticate").
                then().statusCode(200);


        Form form = new Form();
        form.add("password", password);
        form.add("username", name);
        WebResource webResource = client().resource("/rest/accounts");
        JSONObject json = webResource.path("authenticate")
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept("application/json")
                .post(JSONObject.class, form);

        return (String) json.get("token");
    }


}
