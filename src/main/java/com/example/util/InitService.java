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
        if (!danhSach.isEmpty()){
//        for (TaiKhoan tk : danhSach) {
//            String rawPassword = tk.getMatKhau();
//            System.out.println("mk goc:" +rawPassword);
//            if (!rawPassword.startsWith("$2a$")) {
//
//                String encoded = passwordEncoder.encode(rawPassword);
//                System.out.println("mk ma hoa:" +encoded);
//                tk.setMatKhau(encoded);
//                taiKhoanService.update(tk);
//                System.out.println("👉 Gốc: " + rawPassword + " - Đã mã hóa: " + encoded);
//            }
//        }
            for (TaiKhoan tk : danhSach) {
                String rawPassword = tk.getMatKhau();
                System.out.println("📌 Trước mã hóa - ID: " + tk.getIdTaiKhoan() + ", raw: " + rawPassword);

                if (!rawPassword.startsWith("$2a$")) {
                    String encoded = passwordEncoder.encode(rawPassword);
                    System.out.println("🔐 Sau mã hóa - ID: " + tk.getIdTaiKhoan() + ", encoded: " + encoded);

                    tk.setMatKhau(encoded);
                    taiKhoanService.update(tk);

                    // Kiểm tra lại sau update
                    TaiKhoan tkCheck = taiKhoanService.findById(tk.getIdTaiKhoan());
                    System.out.println("📦 DB lưu lại - ID: " + tkCheck.getIdTaiKhoan() + ", mật khẩu: " + tkCheck.getMatKhau());
                }
            }


        }else{
            System.out.println("khong co danh sach");
        }
        System.out.println("✅ Đã mã hóa các mật khẩu chưa mã hóa.");
    }
}
