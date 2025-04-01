package com.example.controller.admin;

import com.example.model.NhanVien;
import com.example.service.NhanVienService;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private NhanVienService nhanVienService;

    // Đường dẫn lưu file ảnh
    private final String uploadDir = "D:/maintenece_motorcycle_management/src/main/resources/static/images";

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

    // 3. Thêm mới NhanVien (kèm file)
    @PostMapping("/upload")
    public ResponseEntity<NhanVien> create(
            @RequestPart("nhanVien") NhanVien nv,
            @RequestPart("file") MultipartFile file) {
        // Xử lý upload file chỉ khi file không rỗng
        if (!file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile);
                nv.setHinhAnh(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Nếu không có file, có thể gán giá trị mặc định hoặc để trống
            nv.setHinhAnh(null);
        }
        // Lưu nhân viên
        NhanVien savedNhanVien = nhanVienService.create(nv);
        return new ResponseEntity<>(savedNhanVien, HttpStatus.CREATED);
    }

    // 4a. Cập nhật nhân viên KHÔNG kèm file
    @PutMapping("/{id}")
    public NhanVien update(@PathVariable String id, @RequestBody NhanVien nv) {
        nv.setIdNhanVien(id);
        nhanVienService.update(nv);
        return nv;
    }

    // 4b. Cập nhật nhân viên CÓ kèm file
    @PutMapping("/updateWithFile/{id}")
    public ResponseEntity<NhanVien> updateWithFile(
            @PathVariable String id,
            @RequestPart("nhanVien") NhanVien nv,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        // Nếu có file mới được gửi lên thì thực hiện upload
        if (file != null && !file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile);
                nv.setHinhAnh(file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Nếu không có file mới, giữ nguyên tên file đã lưu (không upload lại)
            NhanVien existing = nhanVienService.findById(id);
            nv.setHinhAnh(existing.getHinhAnh());
        }
        nv.setIdNhanVien(id);
        nhanVienService.update(nv);
        return new ResponseEntity<>(nv, HttpStatus.OK);
    }

    // 5. Xóa nhân viên
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        nhanVienService.deleteById(id);
    }
}
