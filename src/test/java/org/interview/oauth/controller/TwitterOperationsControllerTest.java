package org.interview.oauth.controller;

import org.hamcrest.CoreMatchers;
import org.interview.oauth.Main;
import org.interview.oauth.api.model.request.TwitterCreateSessionRequest;
import org.interview.oauth.api.model.response.GenericResponse;
import org.interview.oauth.service.JsonParsingService;
import org.interview.oauth.service.impl.JsonParsingServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TwitterOperationsControllerTest {
    static JsonParsingService jsonParsingService;

    @BeforeClass
    public static void setup() {
        jsonParsingService = new JsonParsingServiceImpl();
        Main.main(new String[1]);
    }

    @Test
    public void test_ifTheAuthLinkIsCreatedSuccessful() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:5555/authLink"))
                .header("Content-Type", "application/json").build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponse genericResponse = jsonParsingService.fromJSonToPOJO(response.body(), GenericResponse.class);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("SUCCESS", genericResponse.getResponse().getMessage());
        Assert.assertThat(genericResponse.getBody().toString(),
                CoreMatchers.containsString("https://api.twitter.com/oauth/authorize?oauth_token"));
    }


    @Test
    public void test_ifCreateSessionWithEmptyPinCodeFails() throws IOException, InterruptedException {
        TwitterCreateSessionRequest createSessionRequest = new TwitterCreateSessionRequest("");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonParsingService.toJsonPOJO(createSessionRequest)))
                .uri(URI.create("http://localhost:5555/createSession"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(500, response.statusCode());
        Assert.assertThat(response.body(),
                CoreMatchers.containsString("Unable to authorize access"));
    }

    @Test
    public void test_ifTheSessionListSuccessful() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:5555/sessionList"))
                .header("Content-Type", "application/json").build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponse genericResponse = jsonParsingService.fromJSonToPOJO(response.body(), GenericResponse.class);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("SUCCESS", genericResponse.getResponse().getMessage());
    }

    @Test
    public void test_ifTheListTwitsSuccessful() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:5555/listTwits"))
                .header("Content-Type", "application/json").build();

        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        GenericResponse genericResponse = jsonParsingService.fromJSonToPOJO(response.body(), GenericResponse.class);
        Assert.assertEquals(500, response.statusCode());
        Assert.assertEquals("There is no auth found! First create auth token!",
                genericResponse.getResponse().getMessage());
    }

}
