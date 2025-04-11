package com.fct.visitation;

import com.fct.visitation.security.JwtTokenProvider;
import com.fct.visitation.services.interfaces.*;
import com.fct.visitation.utils.QRCodeGeneratorInterface;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfigurationSupport {
 @Bean
    @Primary
    public JwtTokenProvider jwtTokenProvider() {
        return Mockito.mock(JwtTokenProvider.class);
    }

    @Bean
    @Primary
    public VisitorService visitorService() {
        return Mockito.mock(VisitorService.class);
    }

    @Bean
    @Primary
    public FacilityService facilityService() {
        return Mockito.mock(FacilityService.class);
    }

    @Bean
    @Primary
    public OfficerService officerService() {
        return Mockito.mock(OfficerService.class);
    }

    @Bean
    @Primary
    public PurposeOfVisitService purposeOfVisitService() {
        return Mockito.mock(PurposeOfVisitService.class);
    }
 @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

    @Bean
    public QRCodeGeneratorInterface qrCodeGenerator() {
        QRCodeGeneratorInterface mock = Mockito.mock(QRCodeGeneratorInterface.class);
        when(mock.generateQRCodeImage(any(), anyInt(), anyInt())).thenReturn("MOCK-QR-CODE");
        return mock;
    }
}
