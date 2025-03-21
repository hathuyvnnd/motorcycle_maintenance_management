package com.example.controller.admin;

import com.example.model.NhanVien;
import com.example.service.NhanVienService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    private final NhanVienService nhanVienService;

    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    // 1. Lấy tất cả NhanVien
    @GetMapping
    public List<NhanVien> getAllNhanVien() {
        return nhanVienService.findAll();
    }

    // 2. Lấy 1 NhanVien theo ID
    @GetMapping("/{id}")
    public NhanVien getOne(@PathVariable String id) {
        return nhanVienService.findById(id);
    }

    // 3. Thêm mới NhanVien
    @PostMapping
    public NhanVien create(@RequestBody NhanVien nv) {
        return nhanVienService.create(nv);
    }

    // 4. Cập nhật NhanVien
    @PutMapping("/{id}")
    public NhanVien update(@PathVariable String id, @RequestBody NhanVien nv) {
        // Đảm bảo ID của entity trùng với ID param
        nv.setIdNhanVien(id);
        nhanVienService.update(nv);
        return nv;
    }

    // 5. Xóa NhanVien
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        nhanVienService.deleteById(id);
    }
}
