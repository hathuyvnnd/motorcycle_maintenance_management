package com.example.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.LoaiPhuTungService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import com.example.model.LoaiPhuTung;

@RestController
@RequestMapping("/api/admin/loaiphutung")
public class LoaiPhuTungController {

    @Autowired
    private LoaiPhuTungService loaiPTService;

    @GetMapping
    public List<LoaiPhuTung> getAllLoaiPT() {
        return loaiPTService.findAll();
    }

}
