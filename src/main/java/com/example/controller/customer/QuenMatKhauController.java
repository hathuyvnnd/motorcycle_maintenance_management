package com.example.controller.customer;

import com.example.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping  ("/api/khachhang")
public class QuenMatKhauController {

    @Autowired
    TaiKhoanService tkService;
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        boolean result = tkService.resetPasswordByEmail(email);

        if (result) {
            return ResponseEntity.ok(Map.of("message", "Mật khẩu mới đã được gửi đến email của bạn."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Email không tồn tại trong hệ thống."));
        }
    }

}
