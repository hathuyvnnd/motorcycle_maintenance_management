package com.example.maintenece_motorcycle_management.controller;

import com.example.maintenece_motorcycle_management.model.TaiKhoan;
import com.example.maintenece_motorcycle_management.service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        Optional<TaiKhoan> taiKhoan = accountService.findByID(username);
        if (taiKhoan.isPresent()) {
            if (!taiKhoan.get().getMatKhau().equals(password)) {
                return new ResponseEntity<>(Map.of("message", "Mật khẩu không đúng"), HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(Map.of("idTaiKhoan", taiKhoan.get().getIdTaiKhoan()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("message", "Tài khoản không tồn tại"), HttpStatus.NOT_FOUND);
    }
}