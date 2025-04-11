package com.fct.visitation.services;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.impl.VisitorServiceImpl;
import com.fct.visitation.utils.QRCodeGeneratorInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTest {
    @Mock
    private VisitorRepository visitorRepository;

@Mock
    private QRCodeGeneratorInterface qrCodeGenerator;

    @InjectMocks
    private VisitorServiceImpl visitorService;

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
    }

    @Test
    void testDeleteVisitor() {
        // Prepare: Add a mock visitor before deletion
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));

        // Execute
        visitorService.deleteVisitor(1L);

        // Verify
        verify(visitorRepository).deleteById(1L);
    }

    // Rest of the tests remain the same...
}
