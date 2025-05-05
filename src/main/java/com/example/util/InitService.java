package com.example.util;

import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;
import com.example.service_impl.TaiKhoanServiceImpl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {
    @Autowired
    private TaiKhoanServiceImpl taiKhoanService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        List<TaiKhoan> danhSach = taiKhoanService.findAll();
        for (TaiKhoan tk : danhSach) {
            String rawPassword = tk.getMatKhau();
            System.out.println("rawPassword: " + rawPassword);
            if (!rawPassword.startsWith("$2a$")) {
                String encoded = passwordEncoder.encode(rawPassword);
                System.out.println("encoded: " + encoded);
                tk.setMatKhau(encoded);
                taiKhoanService.update(tk);
                TaiKhoan updatedTaiKhoan = taiKhoanService.findById(tk.getIdTaiKhoan());
                System.out.println("Updated TaiKhoan: " + updatedTaiKhoan.getMatKhau());
            }
        }
        System.out.println("✅ Đã mã hóa các mật khẩu chưa mã hóa.");
    }
}
