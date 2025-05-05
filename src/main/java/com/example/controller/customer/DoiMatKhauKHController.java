package com.example.controller.customer;

import com.example.dto.reponse.MessageResponse;
import com.example.model.TaiKhoan;
import com.example.service.KhachHangService;
import com.example.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/khachhang")
public class DoiMatKhauKHController {
    @Autowired
    TaiKhoanService taiKhoanService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/doimatkhau")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
        String matKhauHienTai = body.get("matKhauHienTai");
        String matKhauMoi = body.get("matKhauMoi");

        String idTaiKhoan = SecurityContextHolder.getContext().getAuthentication().getName();
        TaiKhoan tk = taiKhoanService.findByIdTaiKhoan(idTaiKhoan);

        if (tk == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản không tồn tại"));
        }

        if (!passwordEncoder.matches(matKhauHienTai, tk.getMatKhau())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu hiện tại không đúng"));
        }

        tk.setMatKhau(passwordEncoder.encode(matKhauMoi));
        taiKhoanService.update(tk);

        return ResponseEntity.ok(new MessageResponse("Đổi mật khẩu thành công"));
    }
}
