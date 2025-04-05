package com.fct.visitation.controllers;

import com.fct.visitation.TestConfigurationSupport;
import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.security.JwtTokenProvider;
import com.fct.visitation.services.interfaces.FacilityService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(VisitorController.class)
@Import(TestConfigurationSupport.class)
public class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Visitor visitor;
    private Facility facility;
    private Officer officer;
    private PurposeOfVisit purposeOfVisit;
    private List<Facility> facilities;

    @BeforeEach
    void setUp() {
        // Create mock facility
        facility = new Facility();
        facility.setFacilityId(1L);
        facility.setFacilityName("Test Facility");
        facility.setAddress("Test Address");
        facility.setContactNumber("1234567890");
        facilities = Arrays.asList(facility);

// Create mock officer
        officer = new Officer();
        officer.setOfficerId(1L);
        officer.setName("Test Officer");
        officer.setFacility(facility);

        // Create mock purpose of visit
        purposeOfVisit = new PurposeOfVisit();
        purposeOfVisit.setPurposeId(1L);
        purposeOfVisit.setDescription("Test Purpose");
        purposeOfVisit.setFacility(facility);

        // Create mock visitor
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

        // Mock dependencies
        when(facilityService.findAll()).thenReturn(facilities);
        when(visitorService.registerVisitor(any(Visitor.class))).thenReturn(visitor);
    }
@Test
    @WithMockUser
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/visitor/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/registration"))
                .andExpect(model().attributeExists("visitor"))
                .andExpect(model().attributeExists("facilities"));
    }

    @Test
    @WithMockUser
    void testRegisterVisitor() throws Exception {
        mockMvc.perform(post("/visitor/register")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com")
                .param("phoneNumber", "08012345678")
                .param("nin", "12345678901")
                .param("carType", "OWN")
                .param("facility.facilityId", "1")
                .param("officer.officerId", "1")
                .param("purposeOfVisit.purposeId", "1")
                .param("appointmentDateTime", LocalDateTime.now().plusDays(1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/visitor/confirmation"));
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
        when(visitorService.checkInVisitor(1L)).thenReturn(visitor);

        mockMvc.perform(post("/visitor/checkin")
                .param("visitorId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
