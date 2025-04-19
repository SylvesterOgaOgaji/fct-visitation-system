package com.fct.visitation.services;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.VisitorRepository;
import com.fct.visitation.services.impl.VisitorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTest {
    @Mock
    private VisitorRepository visitorRepository;

    @InjectMocks
    private VisitorServiceImpl visitorService;

    private Visitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new Visitor();
        visitor.setId(1L);
    }

    @Test
    void testDeleteVisitor() {
        // Stub findById if your service checks existence
        //when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));

        // Execute
        visitorService.deleteVisitor(1L);

        // Verify
        verify(visitorRepository).deleteById(1L);
    }
}