package com.fct.visitation.integration;

import com.fct.visitation.BaseIntegrationTest;
import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.FacilityRepository;
import com.fct.visitation.repositories.OfficerRepository;
import com.fct.visitation.repositories.PurposeOfVisitRepository;
import com.fct.visitation.repositories.VisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class VisitorIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private OfficerRepository officerRepository;
@Autowired
    private PurposeOfVisitRepository purposeOfVisitRepository;

    private Facility facility;
    private Officer officer;
    private PurposeOfVisit purposeOfVisit;

    @BeforeEach
    void setUp() {
        // Create test facility
        facility = new Facility();
        facility.setFacilityName("Test Facility");
        facility.setAddress("Test Address");
        facility.setContactNumber("08012345678");
        facility.setEmail("test@facility.com");
        facility = facilityRepository.save(facility);

        // Create test officer
        officer = new Officer();
        officer.setName("Test Officer");
        officer.setPosition("Test Position");
        officer.setDepartment("Test Department");
        officer.setDutyStatus(Officer.DutyStatus.ON_DUTY);
        officer.setFacility(facility);
        officer = officerRepository.save(officer);

        // Create test purpose of visit
        purposeOfVisit = new PurposeOfVisit();
        purposeOfVisit.setDescription("Test Purpose");
        purposeOfVisit.setFacility(facility);
        purposeOfVisit = purposeOfVisitRepository.save(purposeOfVisit);
    }
@Test
    @WithMockUser
    void testVisitorRegistrationFlow() throws Exception {
        // First access the registration form
        mockMvc.perform(get("/visitor/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/registration"))
                .andExpect(model().attributeExists("visitor"))
                .andExpect(model().attributeExists("facilities"));

        // Then submit the form
        mockMvc.perform(post("/visitor/register")
                .with(csrf())
                .param("firstName", "Integration")
                .param("lastName", "Test")
                .param("email", "integration.test@example.com")
                .param("phoneNumber", "08087654321")
                .param("nin", "98765432109")
                .param("carType", "OWN")
                .param("facility.facilityId", facility.getFacilityId().toString())
                .param("officer.officerId", officer.getOfficerId().toString())
                .param("purposeOfVisit.purposeId", purposeOfVisit.getPurposeId().toString())
                .param("appointmentDateTime", LocalDateTime.now().plusDays(1).toString()))
                .andExpect(status().is3xxRedirection());
 // Verify the visitor was created
        Visitor createdVisitor = visitorRepository.findByEmail("integration.test@example.com").orElseThrow();
        
        // Then check the confirmation page
        mockMvc.perform(get("/visitor/confirmation")
                .param("id", createdVisitor.getVisitorId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/confirmation"))
                .andExpect(model().attributeExists("visitor"));
    }
}
