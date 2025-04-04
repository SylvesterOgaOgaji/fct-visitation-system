package com.fct.visitation.controllers;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.FacilityService;
import com.fct.visitation.services.interfaces.OfficerService;
import com.fct.visitation.services.interfaces.PurposeOfVisitService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitorController.class)
public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;

    @MockBean
    private FacilityService facilityService;

    @MockBean
    private OfficerService officerService;

    @MockBean
    private PurposeOfVisitService purposeOfVisitService;

    private Visitor visitor;
    private Facility facility;
    private Officer officer;
    private PurposeOfVisit purposeOfVisit;

    @BeforeEach
    void setUp() {
        facility = new Facility();
        facility.setFacilityId(1L);
        facility.setFacilityName("Test Facility");

        officer = new Officer();
        officer.setOfficerId(1L);
        officer.setName("Test Officer");
        officer.setFacility(facility);

        purposeOfVisit = new PurposeOfVisit();
        purposeOfVisit.setPurposeId(1L);
        purposeOfVisit.setDescription("Test Purpose");
        purposeOfVisit.setFacility(facility);

        visitor = new Visitor();
        visitor.setVisitorId(1L);
        visitor.setFirstName("John");
        visitor.setLastName("Doe");
        visitor.setEmail("john.doe@example.com");
        visitor.setPhoneNumber("08012345678");
        visitor.setNin("12345678901");
        visitor.setCarType(Visitor.CarType.OWN);
        visitor.setFacility(facility);
        visitor.setOfficer(officer);
        visitor.setPurposeOfVisit(purposeOfVisit);
        visitor.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        visitor.setStatus(Visitor.VisitorStatus.PENDING);
        visitor.setQrCode("TEST-QR-CODE");
    }

    @Test
    @WithMockUser
    void testShowRegistrationForm() throws Exception {
        when(facilityService.findAll()).thenReturn(Arrays.asList(facility));

        mockMvc.perform(get("/visitor/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/registration"))
                .andExpect(model().attributeExists("visitor"))
                .andExpect(model().attributeExists("facilities"));
    }

    @Test
    @WithMockUser
    void testRegisterVisitor() throws Exception {
        when(visitorService.registerVisitor(any(Visitor.class))).thenReturn(visitor);

        mockMvc.perform(post("/visitor/register")
                .with(csrf())
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com")
                .param("phoneNumber", "08012345678")
                .param("nin", "12345678901")
                .param("carType", "OWN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/visitor/confirmation?id=1"));
    }

    @Test
    @WithMockUser
    void testShowConfirmation() throws Exception {
        when(visitorService.findById(1L)).thenReturn(Optional.of(visitor));

        mockMvc.perform(get("/visitor/confirmation")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/confirmation"))
                .andExpect(model().attributeExists("visitor"));
    }

    @Test
    @WithMockUser
    void testCheckInVisitor() throws Exception {
        when(visitorService.findByQrCode("TEST-QR-CODE")).thenReturn(Optional.of(visitor));
        when(visitorService.checkInVisitor(1L)).thenReturn(visitor);

        mockMvc.perform(get("/visitor/check-in/TEST-QR-CODE"))
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/checked-in"))
                .andExpect(model().attributeExists("visitor"));
    }
}
