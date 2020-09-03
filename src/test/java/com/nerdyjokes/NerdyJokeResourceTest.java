package com.nerdyjokes;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class NerdyJokeResourceTest {

    @InjectMock
    @RestClient
    NerdyJokeResourceClient client;

    @BeforeEach
    public void setup() {
        JokeResponse.Value value = new JokeResponse.Value(100, "joke", List.of("nerdy"));
        JokeResponse jokeResponse = new JokeResponse("success", value);
        Mockito.when(client.getRandomJoke("nerdy", "Johnny", "Foobar")).thenReturn(jokeResponse);
    }

    @Test
    public void getNerdyJokeEndpoint() {
        final var response =
                "{\"type\":\"success\",\"value\":{\"id\":100,\"joke\":\"joke\",\"categories\":[\"nerdy\"]}}";

        given()
                .accept(MediaType.APPLICATION_JSON)
                .when().get("/api/v1/joke")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(is(response));
    }

}