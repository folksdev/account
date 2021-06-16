package com.folksdev.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.folksdev.account.dto.AccountDtoConverter;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.AccountRepository;
import com.folksdev.account.repository.CustomerRepository;
import com.folksdev.account.service.AccountService;
import com.folksdev.account.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;
import java.util.function.Supplier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port=0",
        "command.line.runner.enabled=false"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private Supplier<UUID> uuidSupplier;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountDtoConverter converter;

    private AccountService service = new AccountService(accountRepository, customerService, converter);
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testCreateAccount_whenCustomerIdExits_shouldCreateAccountAndReturnAccountDto() throws Exception {
        Customer customer = customerRepository.save(new Customer("Anil", "Derbent"));
        CreateAccountRequest request = new CreateAccountRequest(customer.getId(), new BigDecimal(100));

        this.mockMvc.perform(post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance", is(100)))
                .andExpect(jsonPath("$.customer.id", is(customer.getId())))
                .andExpect(jsonPath("$.customer.name", is("Anil")))
                .andExpect(jsonPath("$.customer.surname", is("Derbent")))
                .andExpect(jsonPath("$.transactions", hasSize(1)));

    }

}