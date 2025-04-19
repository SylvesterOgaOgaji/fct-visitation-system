package com.fct.visitation.config;

import com.fct.visitation.models.entity.*;
import com.fct.visitation.models.enums.DutyStatus;
import com.fct.visitation.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private NinVerificationRepository ninVerificationRepository;
    
    @Autowired
    private FacilityRepository facilityRepository;
    
    @Autowired
    private OfficerRepository officerRepository;
    
    @Autowired
    private PurposeOfVisitRepository purposeOfVisitRepository;
    
    @Autowired
    private CheckpointRepository checkpointRepository;

    @Override
    public void run(String... args) throws Exception {
        // Load sample data only if the database is empty
        loadNinVerifications();
        loadFacilities();
        loadCheckpoints();
    }

    private void loadNinVerifications() {
        if (ninVerificationRepository.count() == 0) {
            // Create sample NIN verifications
            NinVerification nin1 = new NinVerification(
                "12345678901",
                "John",
                "Doe",
                LocalDate.of(1990, 5, 15),
                "Male",
                "123 Main Street, Abuja",
                "08012345678",
                true
            );
            
            NinVerification nin2 = new NinVerification(
                "98765432109",
                "Jane",
                "Smith",
                LocalDate.of(1985, 8, 22),
                "Female",
                "456 Park Avenue, Lagos",
                "08087654321",
                true
            );
            
            // Save the NIN verifications
            ninVerificationRepository.saveAll(Arrays.asList(nin1, nin2));
        }
    }
    
    private void loadFacilities() {
        if (facilityRepository.count() == 0) {
            // Create sample facilities
            Facility facility1 = new Facility();
            facility1.setFacilityName("FCT Administration Building");
            facility1.setAddress("Area 11, Garki, Abuja");
            facility1.setContactNumber("08001112222");
            facility1.setEmail("fct-admin@example.com");
            
            Facility facility2 = new Facility();
            facility2.setFacilityName("FCT Development Center");
            facility2.setAddress("Wuse Zone 6, Abuja");
            facility2.setContactNumber("08003334444");
            facility2.setEmail("fct-dev@example.com");
            
            // Save facilities
            facilityRepository.saveAll(Arrays.asList(facility1, facility2));
            
            // Now that we have facilities, create officers and purposes
            loadOfficers(facility1, facility2);
            loadPurposes(facility1, facility2);
        }
    }
    
    private void loadOfficers(Facility facility1, Facility facility2) {
        if (officerRepository.count() == 0) {
            // Create sample officers
            Officer officer1 = new Officer();
            officer1.setFacility(facility1);
            officer1.setFirstName("Michael");
            officer1.setLastName("Johnson");
            officer1.setDepartment("Administration");
            officer1.setDutyStatus(DutyStatus.ON_DUTY); // Using enum directly
            officer1.setEmail("michael.j@example.com");
            officer1.setPhoneNumber("08011122233");
            
            Officer officer2 = new Officer();
            officer2.setFacility(facility1);
            officer2.setFirstName("Sarah");
            officer2.setLastName("Williams");
            officer2.setDepartment("Human Resources");
            officer2.setDutyStatus(DutyStatus.ON_DUTY); // Using enum directly
            officer2.setEmail("sarah.w@example.com");
            officer2.setPhoneNumber("08044455566");
            
            Officer officer3 = new Officer();
            officer3.setFacility(facility2);
            officer3.setFirstName("David");
            officer3.setLastName("Brown");
            officer3.setDepartment("Operations");
            officer3.setDutyStatus(DutyStatus.ON_DUTY); // Using enum directly
            officer3.setEmail("david.b@example.com");
            officer3.setPhoneNumber("08077788899");
            
            // Save officers
            officerRepository.saveAll(Arrays.asList(officer1, officer2, officer3));
        }
    }
    
    private void loadPurposes(Facility facility1, Facility facility2) {
        if (purposeOfVisitRepository.count() == 0) {
            // Create sample purposes of visit
            PurposeOfVisit purpose1 = new PurposeOfVisit();
            purpose1.setCode("MEET_1");
            purpose1.setDescription("Meeting");
            
            PurposeOfVisit purpose2 = new PurposeOfVisit();
            purpose2.setCode("DOC_1");
            purpose2.setDescription("Document Submission");
            
            PurposeOfVisit purpose3 = new PurposeOfVisit();
            purpose3.setCode("INQ_1");
            purpose3.setDescription("Inquiry");
            
            PurposeOfVisit purpose4 = new PurposeOfVisit();
            purpose4.setCode("PROJ_1");
            purpose4.setDescription("Project Discussion");
            
            PurposeOfVisit purpose5 = new PurposeOfVisit();
            purpose5.setCode("TRAIN_1");
            purpose5.setDescription("Training");
            
            // Save purposes
            purposeOfVisitRepository.saveAll(Arrays.asList(purpose1, purpose2, purpose3, purpose4, purpose5));
        }
    }
    
    private void loadCheckpoints() {
        if (checkpointRepository.count() == 0) {
            // Get the facilities (assuming they've been loaded already)
            List<Facility> facilities = facilityRepository.findAll();
            if (facilities.isEmpty()) {
                throw new RuntimeException("No facilities found. Please load facilities first.");
            }
            
            Facility defaultFacility = facilities.get(0); // Use the first facility
            
            // Create sample checkpoints
            Checkpoint checkpoint1 = new Checkpoint();
            checkpoint1.setName("Main Entrance");
            checkpoint1.setLocation("Main gate of the facility");
            checkpoint1.setDescription("Main gate of the facility");
            checkpoint1.setCheckpointType("ENTRY");
            checkpoint1.setIsActive(true);
            
            Checkpoint checkpoint2 = new Checkpoint();
            checkpoint2.setName("Reception");
            checkpoint2.setLocation("Reception area");
            checkpoint2.setDescription("Reception area");
            checkpoint2.setCheckpointType("FACILITY");
            checkpoint2.setIsActive(true);
            
            Checkpoint checkpoint3 = new Checkpoint();
            checkpoint3.setName("Security Office");
            checkpoint3.setLocation("Security clearance point");
            checkpoint3.setDescription("Security clearance point");
            checkpoint3.setCheckpointType("EXIT");
            checkpoint3.setIsActive(true);
            
            // Save checkpoints
            checkpointRepository.saveAll(Arrays.asList(checkpoint1, checkpoint2, checkpoint3));
        }
    }
}