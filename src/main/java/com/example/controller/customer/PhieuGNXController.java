package com.example.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.HoaDon;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service.HoaDonService;
import com.example.service.PhieuDichVuService;
import com.example.service.PhieuGhiNhanTinhTrangXeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping
public class PhieuGNXController {
    @Autowired
    PhieuGhiNhanTinhTrangXeService pgnService;

    
    @GetMapping("/api/tinhtrangxe")
    public PhieuGhiNhanTinhTrangXe getTinhTrangXeByHD(@RequestParam("idPhieuGNX") String idPhieuGNX) {
        return pgnService.getPGNById(idPhieuGNX);
    }
    

    
}
