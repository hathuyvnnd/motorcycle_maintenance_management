package com.example.controller;


import com.example.model.KhachHang;
import com.example.model.LichHen;
import com.example.model.LoaiXe;
import com.example.model.TaiKhoan;
import com.example.service.KhachHangService;
import com.example.service.LichHenService;
import com.example.service.LoaiXeService;
import com.example.service.TaiKhoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final LichHenService lichHenService;
    private final LoaiXeService loaiXeService;
    private final KhachHangService khachHangService;
    private final TaiKhoanService taiKhoanService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@RequestBody LichHen lichHen, @RequestParam("idloaixe") String idLXe, @RequestParam("sdt") String sdt) {
        try {
            TaiKhoan taiKhoan = taiKhoanService.findById(sdt);
            if (taiKhoan == null) {
                return new ResponseEntity<>(Map.of("message", "Bạn chưa đăng nhập"), HttpStatus.UNAUTHORIZED);
            }
            KhachHang khachHang = khachHangService.findTopBySoDienThoai(sdt);
            LoaiXe loaiXe = loaiXeService.findById(idLXe);
            lichHen.setKhachHang(khachHang);
            lichHen.setIdLoaiXe(loaiXe);
            lichHenService.create(lichHen);
            return new ResponseEntity<>(Map.of("message", "Thêm lịch hẹn thành công"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Thêm lịch hẹn thất bại"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<LoaiXe>> getVehicles() {
        return new ResponseEntity<>(loaiXeService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> schedule() {
        List<LichHen> lichHens = lichHenService.findAll();
        return new ResponseEntity<>(Map.of("data", lichHens), HttpStatus.OK);
    }

}