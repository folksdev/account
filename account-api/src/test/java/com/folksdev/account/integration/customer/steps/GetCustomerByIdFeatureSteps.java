package com.folksdev.account.integration.customer.steps;

import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import com.folksdev.account.service.CustomerService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class GetCustomerByIdFeatureSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    private String id = null;
    private ResponseEntity<CustomerDto> response = null;
    private String responseCode = null;

    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler = new RestTemplateResponseErrorHandler();

    @Given("^db contains an user with valid id$")
    public void create_customer_with(){
        Customer customer = customerRepository.save(new Customer("Furkan", "İzm"));
        id = customer.getId();
    }

    @When("^client makes a call to GET '(.*)' with id$")
    public void call_endpoint(String endpoint){
        RestTemplate restTemplate = new RestTemplate();
        response =  restTemplate.getForEntity(String.format("http://localhost:%1$d/%2$s/%3$s", port, endpoint, id), CustomerDto.class);
    }

    @Then("^client receives status code (\\d+)$")
    public void check_status_code(int statusCode){
        if(response == null){
            assertEquals(statusCode, restTemplateResponseErrorHandler.lastErrorCode);
        }else{
            assertEquals(statusCode, response.getStatusCodeValue());
        }

    }

    @Then("^client id is valid$")
    public void check_customer_id(){
        assertEquals(id, response.getBody().getId());
    }

    @When("^client makes a call to GET '(.*)' with invalid$")
    public void call_endpoint_invalid_id(String endpoint){
        String id = "something";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        try{
            response = restTemplate.exchange(String.format("http://localhost:%1$d/%2$s/%3$s", port, endpoint, id), HttpMethod.GET, null, CustomerDto.class, new HashMap<>());
        }catch (Exception e){
            System.out.println(e);
        }

    }

    class RestTemplateResponseErrorHandler implements ResponseErrorHandler{

        private int lastErrorCode;

        public int getLastErrorCode() {
            return lastErrorCode;
        }

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return (
                    clientHttpResponse.getStatusCode().series() == CLIENT_ERROR
                            || clientHttpResponse.getStatusCode().series() == SERVER_ERROR);
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
           lastErrorCode = clientHttpResponse.getRawStatusCode();
        }
    }
}
