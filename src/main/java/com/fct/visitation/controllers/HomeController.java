package com.fct.visitation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        // Return the name of your home page template, e.g. "index"
        return "index";
    }
}
