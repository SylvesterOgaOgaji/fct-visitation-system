package com.fct.visitation.integration;

import com.fct.visitation.VisitationApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = VisitationApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VisitorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testVisitorRegistrationFlow() throws Exception {
        mockMvc.perform(post("/visitors/register")
               .param("fullName", "Sylvester")
               .param("email", "slyokoh@gmail.com")
               .param("phoneNumber", "08012345678"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/visitors/confirmation"));
    }
}