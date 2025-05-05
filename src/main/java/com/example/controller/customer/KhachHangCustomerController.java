package com.example.controller.customer;

import com.example.dto.request.khachhang.KhachHangDTO;
import com.example.model.KhachHang;
import com.example.model.TaiKhoan;
import com.example.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping  ("/api")
public class KhachHangCustomerController {

    @Autowired
    private KhachHangService khService;


    @PostMapping("/dangky")
    public ResponseEntity<?> dangKyKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
        KhachHang kh = khService.getByEmail(khachHangDTO.getEmail());
        try {
            if (khService.exitsById(khachHangDTO.getSoDienThoai())) {
                // Nếu tài khoản đã tồn tại, trả về lỗi
                return ResponseEntity.badRequest().body("Số điện thoại đã được sử dụng.");
            }
            if (kh != null) {
                // Nếu email đã tồn tại, trả về lỗi
                return ResponseEntity.badRequest().body("Email đã được sử dụng.");
            }
            KhachHang khachHang = khService.dangKyKhachHang(
                    khachHangDTO.getSoDienThoai(),
                    khachHangDTO.getMatKhau(),
                    khachHangDTO.getHoTen(),
                    khachHangDTO.getDiaChi(),
                    khachHangDTO.getEmail()
            );
            return ResponseEntity.ok(khachHang);  // Trả về thông tin khách hàng vừa tạo
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Đăng ký thất bại: " + e.getMessage());
        }
    }

}
