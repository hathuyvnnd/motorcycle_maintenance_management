package com.example.controller.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.LoaiPhuTungService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import com.example.model.LoaiPhuTung;
@RestController
@RequestMapping  ("/api/khachhang")
public class LoaiPhuTungKHRest {

    @Autowired
    private LoaiPhuTungService loaiPTService;
    
    @GetMapping("/loaiphutung")
    public List<LoaiPhuTung> getAllLoaiPT() {
        List<LoaiPhuTung> list = loaiPTService.findAll();
        System.out.println("Danh sách loại phụ tùng: "+list);
        return loaiPTService.findAll();
    }
    
}
