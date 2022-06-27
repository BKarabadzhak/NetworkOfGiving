package org.finaltask.networkofgiving.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finaltask.networkofgiving.dtos.request.CustomerToRegister;
import org.finaltask.networkofgiving.dtos.request.LoginRequest;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-scripts/create-customer-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-scripts/delete-customer-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void givenCustomer_whenLogin_thenReturnLoginCustomerProperties() throws Exception {

        LoginRequest loginRequest = new LoginRequest("Ivan", "123456");

        MockHttpServletRequestBuilder data = post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest))
                .accept("application/json");

        this.mockMvc.perform(data)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.token").value(IsNull.notNullValue()))
                .andExpect(jsonPath("$.userResponse").value(IsNull.notNullValue()));
    }

    @Test
    public void givenNonexistentCustomer_whenLogin_thenReturnBadRequest() throws Exception {

        LoginRequest loginRequest = new LoginRequest("Vasko", "123456");

        MockHttpServletRequestBuilder data = post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest))
                .accept("application/json");

        this.mockMvc.perform(data)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Customer is not registered in system."));
    }

    @Test
    public void givenNewCustomer_thenRegister_thenReturnMessageSuccess() throws Exception {
        CustomerToRegister customerToRegister = new CustomerToRegister("Nedko", "123456", "Nedko", 21, "Male",
                "Bulgaria");

        MockHttpServletRequestBuilder data = post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerToRegister))
                .accept("application/json");

        ResultActions result =
                this.mockMvc.perform(data)
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType("application/json"))
                        .andExpect(jsonPath("$.userResponse").value(IsNull.notNullValue()));

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        HashMap<String, String> message = (HashMap<String, String>) jsonParser.parseMap(resultString).get("messageResponse");
        String messageText = message.get("message");
        Assert.assertEquals(messageText, "Customer registered successfully!");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}