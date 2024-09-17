package com.unq.dapp_grupo_e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unq.dapp_grupo_e.factories.UserRegisterFactory;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

     
    @Test
    void userRegisterValidReturns200() throws Exception{
        var validUser = UserRegisterFactory.anyUserRegister();

        mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validUser)))
                    .andExpect(status().isOk());
    }
    
    @Test
    void userRegisterWithInvalidNameLength() throws Exception {
        var invalidUser = UserRegisterFactory.createWithName("AI");

        mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());

    }

    @Test
    void userRegisterWithInvalidSurnameLength() throws Exception {
        var invalidUser = UserRegisterFactory.createWithSurname("toomuchcharactersforsurnamefield");

        mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());

    }

    @Test
    void userRegisterWithInvalidPasswordLength() throws Exception {
        var invalidUser = UserRegisterFactory.createWithPassword("Try");

        mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void userRegisterWithMissingUppercasePassword() throws Exception {
        var invalidUser = UserRegisterFactory.createWithPassword("notvalid");

        mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void userRegisterWithMissingLowercasePassword() throws Exception {
        var invalidUser = UserRegisterFactory.createWithPassword("NOTVALID");

        mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());
    }

}
