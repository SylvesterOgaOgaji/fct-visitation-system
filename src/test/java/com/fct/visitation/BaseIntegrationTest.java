package com.fct.visitation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = {VisitationApplication.class, TestConfigurationSupport.class})
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {
}
