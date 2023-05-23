package com.hc.train.cucumber.cucumberglue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;

public class CucumberMySteps {

    @LocalServerPort
    String port;

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> lastResponse;

    @When("the client calls endpoint {string}")
    public void whenClientCalls(String url) {
        try {
            lastResponse = restTemplate.exchange("http://localhost:" + port + url, HttpMethod.GET, null,
                    String.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            //httpClientErrorException.printStackTrace();
            HttpStatus statusCode = httpClientErrorException.getStatusCode();
            String responseBody = httpClientErrorException.getResponseBodyAsString();
            // You can also extract other relevant information like headers, etc.

            // Create the ResponseEntity
            lastResponse = new ResponseEntity<>(responseBody, statusCode);
        }
    }

    @Then("response status code is {int}")
    public void thenStatusCodee(int expected) {
       Assertions.assertNotNull(lastResponse);
       Assertions.assertNotNull(lastResponse.getStatusCode());
        assertThat("status code is" + expected,
                lastResponse.getStatusCodeValue() == expected);
    }

    @Then("returned string should be {string}")
    public void thenStringIs(String expected) {
        Assertions.assertEquals(expected, lastResponse.getBody());
        assertThat("Returned string is " + expected,
                expected.equalsIgnoreCase(lastResponse.getBody()));
    }
}
