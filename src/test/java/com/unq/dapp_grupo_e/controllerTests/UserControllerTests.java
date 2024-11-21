package com.unq.dapp_grupo_e.controllerTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unq.dapp_grupo_e.factory.factoriesdto.UserRegisterFactory;
import com.unq.dapp_grupo_e.service.UserService;

@ActiveProfiles("test") 
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @WithMockUser(username = "non85@mail.com", password = "Non&noN")
    @Test
    void userRegisterValidReturns200() throws Exception{
        userService.deleteAllUsers();
        var validUser = UserRegisterFactory.anyUserRegister();

        mockMvc.perform(post("/authUser/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validUser)))
                    .andExpect(status().isOk());
    }
    
    @WithMockUser(username = "non85@mail.com", password = "Non&noN")
    @Test
    void userRegisterWithInvalidNameLength() throws Exception {
        var invalidUser = UserRegisterFactory.createWithName("AI");

        mockMvc.perform(post("/authUser/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());

    }

    @WithMockUser(username = "non85@mail.com", password = "Non&noN")
    @Test
    void userRegisterWithInvalidSurnameLength() throws Exception {
        var invalidUser = UserRegisterFactory.createWithSurname("toomuchcharactersforsurnamefield");

        mockMvc.perform(post("/authUser/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());

    }

    @WithMockUser(username = "non85@mail.com", password = "Non&noN")
    @ParameterizedTest
    @ValueSource(strings = {"Try", "notvalid", "NOTVALID"})
    void userRegisterInvalidForMissingRequerimentsForPassword(String passwordTry) throws Exception {
        var invalidUser = UserRegisterFactory.createWithPassword(passwordTry);

        mockMvc.perform(post("/authUser/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidUser)))
                    .andExpect(status().isBadRequest());
        
    }

}
