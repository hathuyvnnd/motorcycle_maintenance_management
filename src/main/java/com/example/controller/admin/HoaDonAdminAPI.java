package com.example.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.HoaDon;
import com.example.service.HoaDonService;

@RestController
@RequestMapping("/api/admin/hoa_don")
public class HoaDonAdminAPI {
    @Autowired
    private HoaDonService hoaDonService;

    // Lấy tất cả hoá đơn
    @GetMapping
    public List<HoaDon> getAllHoaDon() {
        return hoaDonService.findAll();
    }
}
