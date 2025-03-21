package com.example.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeAdminController {

    @GetMapping({ "", "/" })
    public String index() {
        // Forward mọi request con của /admin đến index.html trong thư mục static/admin/
        // AngularJS sẽ xử lý các route con sau đó (employee, statistics, account, v.v.)
        return "forward:/admin/index.html";
    }
}
