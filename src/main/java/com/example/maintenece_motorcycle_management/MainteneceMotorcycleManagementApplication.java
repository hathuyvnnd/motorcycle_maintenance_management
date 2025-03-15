package com.example.maintenece_motorcycle_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class MainteneceMotorcycleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainteneceMotorcycleManagementApplication.class, args);
    }

    @Controller
    public class HomeController {
        @GetMapping("/home")
        public String homePage(Model model) {
            model.addAttribute("message", "Welcome to the Home Page!");
            return "/admin/abc"; // Trả về tên của file HTML (home.html) trong thư mục templates
        }
    }

}
