package com.example.controller.employeectrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/giaodien")
public class StaffController {
    @GetMapping("/nhanvien")
    public String showHomeAdmin(){
        return "forward:/employee/a.html";
    }
    @GetMapping("/test")
    public String showHomeUploadNhanVien(){
        return "forward:/employee/content/testupload.html";
    }
}
