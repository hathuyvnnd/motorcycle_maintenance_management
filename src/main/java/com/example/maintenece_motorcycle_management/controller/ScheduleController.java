package com.example.maintenece_motorcycle_management.controller;

import com.example.maintenece_motorcycle_management.model.KhachHang;
import com.example.maintenece_motorcycle_management.model.LichHen;
import com.example.maintenece_motorcycle_management.model.LoaiXe;
import com.example.maintenece_motorcycle_management.model.TaiKhoan;
import com.example.maintenece_motorcycle_management.service.AccountService;
import com.example.maintenece_motorcycle_management.service.CustomerService;
import com.example.maintenece_motorcycle_management.service.ScheduleService;
import com.example.maintenece_motorcycle_management.service.VehicleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final AccountService accountService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@RequestBody LichHen lichHen, @RequestParam("idloaixe") String idLXe, @RequestParam("account") String account) {
        try {
            Optional<TaiKhoan> taiKhoan = accountService.findByID(account);
            if (taiKhoan.isEmpty()) {
                return new ResponseEntity<>(Map.of("message", "Bạn chưa đăng nhập"), HttpStatus.UNAUTHORIZED);
            }
            KhachHang khachHang = customerService.findByIdKhachHang(account)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
            LoaiXe loaiXe = vehicleService.findById(idLXe)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy loại xe"));
            lichHen.setKhachHang(khachHang);
            lichHen.setIdLoaiXe(loaiXe);
            scheduleService.save(lichHen);
            return new ResponseEntity<>(Map.of("message", "Thêm lịch hẹn thành công"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Thêm lịch hẹn thất bại"), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/vehicles")
    public ResponseEntity<List<LoaiXe>> getVehicles() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }
}