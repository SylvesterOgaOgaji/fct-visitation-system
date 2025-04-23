package com.fct.visitation.controllers;

import com.fct.visitation.models.entity.SecurityPersonnel;
import com.fct.visitation.repositories.SecurityPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/security-personnel")
@PreAuthorize("hasRole('ADMIN')")
public class SecurityPersonnelController {
    
    @Autowired
    private SecurityPersonnelRepository securityPersonnelRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public String listSecurityPersonnel(Model model) {
        List<SecurityPersonnel> personnel = securityPersonnelRepository.findAll();
        model.addAttribute("personnel", personnel);
        return "security/personnel/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("securityPersonnel", new SecurityPersonnel());
        return "security/personnel/create";
    }
    
    @PostMapping("/create")
    public String createSecurityPersonnel(@ModelAttribute SecurityPersonnel securityPersonnel) {
        securityPersonnel.setPasswordHash(passwordEncoder.encode(securityPersonnel.getPasswordHash()));
        securityPersonnelRepository.save(securityPersonnel);
        return "redirect:/security-personnel";
    }
}