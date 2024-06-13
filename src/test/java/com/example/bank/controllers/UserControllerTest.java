package com.example.bank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bank.domain.user.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import com.example.bank.domain.user.User;
import com.example.bank.dtos.UserDTO;
import com.example.bank.services.UserService;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    private User user;
    private UserDTO userData;
    private  static final String DOCUMENT = "12345678900";

    @BeforeEach
    public void setup() {

        BigDecimal balance = new BigDecimal(2000);
         userData = new UserDTO(
                "Pablo",
                "Fernandez",
                DOCUMENT,
                "pablo@gmail.com",
                "123456789",
                UserType.COMMON
        );

        user = new User(userData);
    }

    @Test
    void itShouldCreateUser() throws Exception {
        when(service.createUser(userData)).thenReturn(user);

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept("*/*")
                .content(mapper.writeValueAsString(userData))
        );

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.document", is(DOCUMENT)));
    }
}
