package com.fct.visitation;

import com.fct.visitation.security.JwtTokenProvider;
import com.fct.visitation.services.interfaces.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
