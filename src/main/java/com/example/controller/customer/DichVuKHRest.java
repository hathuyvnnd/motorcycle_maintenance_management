package com.example.controller.customer;

import com.example.model.DichVu;
import com.example.service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping  ("/api/khachhang")
public class DichVuKHRest {

    @Autowired
    private DichVuService dvService;

    @GetMapping("/dichvu")
    public List<DichVu> getAllDV() {
        return dvService.findAll();
    }

}

