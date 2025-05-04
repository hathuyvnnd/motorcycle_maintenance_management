package com.example.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.KhachHang;
import com.example.model.LichHen;
import com.example.service.LichHenService;

@RestController
@RequestMapping("/api/admin/lich_hen")
public class LichHenAdminAPI {
    @Autowired
    private LichHenService lichHenService;

    // Lấy tất cả LichHen
    @GetMapping
    public List<LichHen> getAllLichHen() {
        return lichHenService.findAll();
    }

}
