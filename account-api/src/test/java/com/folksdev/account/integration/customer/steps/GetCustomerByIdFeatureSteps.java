package com.folksdev.account.integration.customer.steps;


import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;
import static org.junit.Assert.*;

public class GetCustomerByIdFeatureSteps {
    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private CustomerRepository customerRepository;

    private ResponseEntity<CustomerDto> entity = null;
    private String id = null;

    @Given("^customer exists in db$")
    public void init_customer() {
        Customer customer = customerRepository.save(new Customer("Cagri", "Dursun"));
        id = customer.getId();
    }

    @When("^client calls GET '(.*)'$")
    public void client_calls(String endpoint) {
        RestTemplate restTemplate = new RestTemplate();
        entity = restTemplate
                .getForEntity(String.format("http://localhost:%1$d/%2$s/%3$s", randomServerPort, endpoint, id), CustomerDto.class);
    }

    @Then("^client receives status code (\\d+)$")
    public void client_receives(int statusCode) {
        assertEquals(statusCode, entity.getStatusCodeValue());
    }

    @And("^client gets customer with correct id$")
    public void client_receives_customer() {
        assertEquals(id, entity.getBody().getId());
    }
}
