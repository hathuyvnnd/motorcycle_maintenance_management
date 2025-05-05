package com.example.util;

import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        List<TaiKhoan> danhSach = taiKhoanService.findAll();
        for (TaiKhoan tk : danhSach) {
            String rawPassword = tk.getMatKhau();
            if (!rawPassword.startsWith("$2a$")) {
                String encoded = passwordEncoder.encode(rawPassword);
                tk.setMatKhau(encoded);
                taiKhoanService.update(tk);
            }
        }
        System.out.println("✅ Đã mã hóa các mật khẩu chưa mã hóa.");
    }
}
