package com.example.crud_rest_api;

import com.example.crud_rest_api.controller.UserController;
import com.example.crud_rest_api.domain.User;
import com.example.crud_rest_api.service.UserService;
import com.example.crud_rest_api.validation.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("john.doe@example.com");
        newUser.setAge(30);
        newUser.setActive(true);

        given(userService.createUser(any(User.class))).willReturn(newUser);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(convertToJsonString(newUser))).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Doe"))).andExpect(jsonPath("$.email", is("john.doe@example.com"))).andExpect(jsonPath("$.age", is(30))).andExpect(jsonPath("$.active", is(true)));

        verify(userService, times(1)).createUser(any(User.class));
    }


    @Test
    public void testCreateUserWithWrongEmailFormat() throws Exception {

        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("johndoeex§§ample555");
        newUser.setAge(30);
        newUser.setActive(true);

        given(userService.createUser(any(User.class))).willReturn(newUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJsonString(newUser)))
                .andExpect(status().isBadRequest());


        verify(userService, times(0)).createUser(any(User.class));
    }


    private String convertToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateUserWithNullEmail() {
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail(null);
        newUser.setAge(30);
        newUser.setActive(true);


        when(userValidator.isValidEmail(null)).thenReturn(false);
        ResponseEntity<EntityModel<User>> response = userController.createUser(newUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}


