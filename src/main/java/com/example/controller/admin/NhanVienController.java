package com.example.controller.admin;

import com.example.model.NhanVien;
import com.example.service.NhanVienService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    private final NhanVienService nhanVienService;
    private final String uploadDir = "C:/TN/Workspace/MotorBike/motorcycle_maintenance_management/src/main/resources/static/images/";

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
    // @PostMapping
    // public NhanVien create(@RequestBody NhanVien nv) {
    // return nhanVienService.create(nv);
    // }
    // 3. Thêm mới NhanVien với upload hình ảnh
    @PostMapping("/upload")
    public ResponseEntity<NhanVien> create(
            @RequestPart("nhanVien") NhanVien nv,
            @RequestPart("file") MultipartFile file) {

        // Xử lý lưu file vào thư mục images của dự án
        if (!file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile); // Lưu file lên server
                nv.setHinhAnh(file.getOriginalFilename()); // Lưu tên file (hoặc đường dẫn tương đối nếu cần)
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Thêm nhân viên
        NhanVien savedNhanVien = nhanVienService.create(nv);
        return new ResponseEntity<>(savedNhanVien, HttpStatus.CREATED);
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
