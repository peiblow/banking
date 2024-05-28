package com.example.bank.controllers.integrationtests;

import com.example.bank.config.TestConfigs;
import com.example.bank.domain.user.User;
import com.example.bank.domain.user.UserType;
import com.example.bank.dtos.UserDTO;
import com.example.bank.integrationtests.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;

    private static User user;
    private static UserDTO userData;

    private  static final String DOCUMENT = "123456789";

    @BeforeAll
    public static void setup(){
        BigDecimal balance = new BigDecimal(2000);

        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/users")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        userData = new UserDTO(
                "test",
                "test",
                DOCUMENT,
                "p@gmail.com",
                balance,
                "123456",
                UserType.COMMON
        );
    }

    @Test
    @Order(1)
    void itShouldCreateUserAndReturnUserObject () throws JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(userData)
                .when()
                    .post()
                .then()
                    .statusCode(201)
                        .extract()
                            .body()
                                .asString();

        User createdUser = mapper.readValue(content, User.class);
        user = createdUser;

        Assertions.assertNotNull(createdUser.getId());
        Assertions.assertNotNull(createdUser.getDocument());

        Assertions.assertTrue(createdUser.getId() > 0);

        Assertions.assertEquals("123456789", createdUser.getDocument());
    }
}
