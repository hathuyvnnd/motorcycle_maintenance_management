package com.example.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.KhachHang;
import com.example.service.KhachHangService;

@RestController
@RequestMapping("/api/admin/khachhang")
public class KhachHangAdminAPI {
    @Autowired
    private KhachHangService khachHangService;

    // public KhachHangController(KhachHangService khachHangService) {
    // this.khachHangService = khachHangService;
    // }

    // 1. Lấy tất cả KhachHang
    @GetMapping
    public List<KhachHang> getAllKhachHang() {
        return khachHangService.findAll();
    }

    // 2. Lấy 1 KhachHang theo ID
    @GetMapping("/{id}")
    public KhachHang getOne(@PathVariable String id) {
        return khachHangService.findById(id);
    }
}
