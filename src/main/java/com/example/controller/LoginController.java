package com.example.controller;


import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final TaiKhoanService taiKhoanService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        TaiKhoan taiKhoan = taiKhoanService.findById(username);
        if (taiKhoan != null) {
            if (!taiKhoan.getMatKhau().equals(password)) {
                return new ResponseEntity<>(Map.of("message", "Mật khẩu không đúng"), HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(Map.of("idTaiKhoan", taiKhoan.getIdTaiKhoan()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("message", "Tài khoản không tồn tại"), HttpStatus.NOT_FOUND);
    }
}