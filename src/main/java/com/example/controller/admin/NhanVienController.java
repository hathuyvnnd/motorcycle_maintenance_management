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

    // public NhanVienController(NhanVienService nhanVienService) {
    // this.nhanVienService = nhanVienService;
    // }

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

        // Xử lý lưu file vào thư mục images
        if (!file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile);
                nv.setHinhAnh(file.getOriginalFilename());
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
    public NhanVien updateWithFile(
            @PathVariable String id,
            @RequestPart("nhanVien") NhanVien nv,
            @RequestPart("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile);
                nv.setHinhAnh(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi upload file", e);
            }
        } else {
            // Nếu không có file mới, giữ nguyên ảnh cũ
            NhanVien existing = nhanVienService.findById(id);
            nv.setHinhAnh(existing.getHinhAnh());
        }

        nv.setIdNhanVien(id);
        nhanVienService.update(nv);
        return nv;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        nhanVienService.deleteById(id);
    }
}
