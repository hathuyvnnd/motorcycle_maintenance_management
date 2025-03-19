package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/statistics")
    public String getStatisticsPage() {
        return "admin/statistics";
    }

    @GetMapping("/employee")
    public String getEmployeePage() {
        return "admin/employee";
    }

    @GetMapping("/customer")
    public String getCustomerPage() {
        return "admin/customer";
    }

    @GetMapping("/account")
    public String getAccountPage() {
        return "admin/account";
    }

    @GetMapping("/service")
    public String getServicePage() {
        return "admin/service";
    }

    @GetMapping("/accessory")
    public String getAccessoryPage() {
        return "admin/accessory";
    }

    @GetMapping("/booking")
    public String getBookingPage() {
        return "admin/booking";
    }

    @GetMapping("/invoice")
    public String getInvoicePage() {
        return "admin/invoice";
    }
}
