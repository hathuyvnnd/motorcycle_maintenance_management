package com.example.maintenece_motorcycle_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DayLaController {

    @GetMapping("")
    public String index() {
        return "customer/index";
    }
}
