package com.example.controller.employeectrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nhanvien")
public class StaffController {
    @GetMapping("/giaodien")
    public String showHomeAdmin(){
        return "forward:/employee/a.html";
    }}
