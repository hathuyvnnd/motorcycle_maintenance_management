package com.example.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.TaiKhoan;
import com.example.model.TaiKhoanNhanVien;
import com.example.service.TaiKhoanService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/taikhoan")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    // 1. Lấy tất cả tài khoản
    @GetMapping
    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanService.findAll();
    }

    // 2. Lấy 1 tài khoản theo ID
    @GetMapping("/{id}")
    public TaiKhoan getOne(@PathVariable String id) {
        return taiKhoanService.findById(id);
    }

    // 3. Thêm mới tài khoản
    @PostMapping("/add_account")
    public TaiKhoan create(@RequestBody TaiKhoan taiKhoan) {
        return taiKhoanService.create(taiKhoan);
    }

    // 4. Cập nhật tài khoản
    @PutMapping("/update/{id}")
    public TaiKhoan update(@PathVariable String id, @RequestBody TaiKhoan taiKhoan) {
        if (taiKhoanService.exitsById(id)) {
            taiKhoan.setIdTaiKhoan(id);
            taiKhoanService.update(taiKhoan);
        }
        return taiKhoan;
    }

    // 5. Xóa tài khoản
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        taiKhoanService.deleteById(id);
    }

    @GetMapping("/available")
    public List<TaiKhoan> getAvailableAccounts() {
        List<TaiKhoan> allAccounts = taiKhoanService.findAll();
        List<TaiKhoan> available = allAccounts.stream()
                .filter(acc -> "Nhân viên".equalsIgnoreCase(acc.getVaiTro()))
                .filter(acc -> {
                    if (acc instanceof TaiKhoanNhanVien) {
                        return ((TaiKhoanNhanVien) acc).getNhanVien() == null;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return available;
    }
}
