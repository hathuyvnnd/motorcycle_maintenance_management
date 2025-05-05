package com.example.controller.customer;

import com.example.dto.reponse.TaiKhoanDN;
import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;
import com.example.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class DangNhapController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private JwtToken jwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TaiKhoan taikhoanLogin) {
        System.out.println("Mật khẩu nhận từ client: " + taikhoanLogin.getIdTaiKhoan());
        System.out.println("Mật khẩu nhận từ client: " + taikhoanLogin.getMatKhau());
        TaiKhoan tk = taiKhoanService.findById(taikhoanLogin.getIdTaiKhoan());
        System.out.println("mat khau khi tim kiem" + tk.getMatKhau());
        if (tk != null && passwordEncoder.matches(taikhoanLogin.getMatKhau(),tk.getMatKhau())) {
            String token = jwt.generateToken(tk.getIdTaiKhoan(), tk.getVaiTro());
            return ResponseEntity.ok(new TaiKhoanDN(token, tk.getVaiTro(),tk.getIdTaiKhoan()));
//            return ResponseEntity.status(400).body("So sanh mat khau thông tin đăng nhập");

        }


        return ResponseEntity.status(401).body("Sai thông tin đăng nhập");
    }

}
