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
    void testRegisterVisitor() {
        // Prepare
        when(qrCodeGenerator.generateQRCodeImage(any(), anyInt(), anyInt())).thenReturn("MOCK-QR-CODE");
        when(visitorRepository.save(any(Visitor.class))).thenReturn(visitor);

        // Execute
        Visitor registeredVisitor = visitorService.registerVisitor(visitor);

        // Verify
        assertNotNull(registeredVisitor);
        assertEquals("John", registeredVisitor.getFirstName());
        assertEquals(Visitor.VisitorStatus.PENDING, registeredVisitor.getStatus());
        assertNotNull(registeredVisitor.getQrCode());
        verify(visitorRepository).save(any(Visitor.class));
        verify(qrCodeGenerator).generateQRCodeImage(any(), anyInt(), anyInt());
    }

@Test
    void testFindById() {
        // Prepare
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));

        // Execute
        Optional<Visitor> foundVisitor = visitorService.findById(1L);

        // Verify
        assertTrue(foundVisitor.isPresent());
        assertEquals("John", foundVisitor.get().getFirstName());
        verify(visitorRepository).findById(1L);
    }

    @Test
    void testFindByQrCode() {
        // Prepare
        when(visitorRepository.findByQrCode("TEST-QR-CODE")).thenReturn(Optional.of(visitor));

        // Execute
        Optional<Visitor> foundVisitor = visitorService.findByQrCode("TEST-QR-CODE");

// Verify
        assertTrue(foundVisitor.isPresent());
        assertEquals("John", foundVisitor.get().getFirstName());
        verify(visitorRepository).findByQrCode("TEST-QR-CODE");
    }

    @Test
    void testFindAll() {
        // Prepare
        when(visitorRepository.findAll()).thenReturn(Arrays.asList(visitor));

        // Execute
        List<Visitor> visitors = visitorService.findAll();

        // Verify
        assertFalse(visitors.isEmpty());
        assertEquals(1, visitors.size());
        assertEquals("John", visitors.get(0).getFirstName());
        verify(visitorRepository).findAll();
    }

@Test
    void testFindByStatus() {
        // Prepare
        when(visitorRepository.findAll()).thenReturn(Arrays.asList(visitor));

        // Execute
        List<Visitor> visitors = visitorService.findByStatus(Visitor.VisitorStatus.PENDING);

        // Verify
        assertFalse(visitors.isEmpty());
        assertEquals(1, visitors.size());
        assertEquals(Visitor.VisitorStatus.PENDING, visitors.get(0).getStatus());
    }
@Test
    void testCheckInVisitor() {
        // Prepare
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(visitorRepository.save(any(Visitor.class))).thenAnswer(i -> {
            Visitor v = i.getArgument(0);
            assertEquals(Visitor.VisitorStatus.CHECKED_IN, v.getStatus());
            return v;
        });

        // Execute
        Visitor checkedInVisitor = visitorService.checkInVisitor(1L);

        // Verify
        assertNotNull(checkedInVisitor);
        assertEquals(Visitor.VisitorStatus.CHECKED_IN, checkedInVisitor.getStatus());
        verify(visitorRepository).findById(1L);
        verify(visitorRepository).save(any(Visitor.class));
    }

@Test
    void testCompleteVisit() {
        // Prepare
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(visitorRepository.save(any(Visitor.class))).thenAnswer(i -> {
            Visitor v = i.getArgument(0);
            assertEquals(Visitor.VisitorStatus.COMPLETED, v.getStatus());
            return v;
        });

        // Execute
        Visitor completedVisitor = visitorService.completeVisit(1L);

        // Verify
        assertNotNull(completedVisitor);
        assertEquals(Visitor.VisitorStatus.COMPLETED, completedVisitor.getStatus());
        verify(visitorRepository).findById(1L);
        verify(visitorRepository).save(any(Visitor.class));
    }
 @Test
    void testDeleteVisitor() {
        // Execute
        visitorService.deleteVisitor(1L);

        // Verify
        verify(visitorRepository).deleteById(1L);
    }

    @Test
    void testGenerateQrCode() {
        // Prepare
        when(qrCodeGenerator.generateQRCodeImage(any(), anyInt(), anyInt())).thenReturn("MOCK-QR-CODE");

        // Execute
        String qrCode = visitorService.generateQrCode(visitor);

        // Verify
        assertNotNull(qrCode);
        assertEquals("MOCK-QR-CODE", qrCode);
        verify(qrCodeGenerator).generateQRCodeImage(any(), anyInt(), anyInt());
    }
}
