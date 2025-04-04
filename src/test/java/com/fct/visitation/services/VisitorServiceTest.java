package com.fct.visitation.services;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.impl.VisitorServiceImpl;
import com.fct.visitation.utils.QRCodeGenerator;
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
    private QRCodeGenerator qrCodeGenerator;

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
        visitor.setQrCode("TEST-QR-CODE");
    }

    @Test
    void testRegisterVisitor() {
        when(visitorRepository.save(any(Visitor.class))).thenReturn(visitor);

        Visitor registeredVisitor = visitorService.registerVisitor(visitor);

        assertNotNull(registeredVisitor);
        assertEquals("John", registeredVisitor.getFirstName());
        assertEquals(Visitor.VisitorStatus.PENDING, registeredVisitor.getStatus());
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }

    @Test
    void testFindById() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));

        Optional<Visitor> foundVisitor = visitorService.findById(1L);

        assertTrue(foundVisitor.isPresent());
        assertEquals("John", foundVisitor.get().getFirstName());
        verify(visitorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByQrCode() {
        when(visitorRepository.findByQrCode("TEST-QR-CODE")).thenReturn(Optional.of(visitor));

        Optional<Visitor> foundVisitor = visitorService.findByQrCode("TEST-QR-CODE");

        assertTrue(foundVisitor.isPresent());
        assertEquals("John", foundVisitor.get().getFirstName());
        verify(visitorRepository, times(1)).findByQrCode("TEST-QR-CODE");
    }

    @Test
    void testFindAll() {
        when(visitorRepository.findAll()).thenReturn(Arrays.asList(visitor));

        List<Visitor> visitors = visitorService.findAll();

        assertFalse(visitors.isEmpty());
        assertEquals(1, visitors.size());
        assertEquals("John", visitors.get(0).getFirstName());
        verify(visitorRepository, times(1)).findAll();
    }

    @Test
    void testCheckInVisitor() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(visitorRepository.save(any(Visitor.class))).thenAnswer(i -> {
            Visitor v = i.getArgument(0);
            assertEquals(Visitor.VisitorStatus.CHECKED_IN, v.getStatus());
            return v;
        });

        Visitor checkedInVisitor = visitorService.checkInVisitor(1L);

        assertNotNull(checkedInVisitor);
        assertEquals(Visitor.VisitorStatus.CHECKED_IN, checkedInVisitor.getStatus());
        verify(visitorRepository, times(1)).findById(1L);
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }

    @Test
    void testCompleteVisit() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(visitorRepository.save(any(Visitor.class))).thenAnswer(i -> {
            Visitor v = i.getArgument(0);
            assertEquals(Visitor.VisitorStatus.COMPLETED, v.getStatus());
            return v;
        });

        Visitor completedVisitor = visitorService.completeVisit(1L);

        assertNotNull(completedVisitor);
        assertEquals(Visitor.VisitorStatus.COMPLETED, completedVisitor.getStatus());
        verify(visitorRepository, times(1)).findById(1L);
        verify(visitorRepository, times(1)).save(any(Visitor.class));
    }
}
