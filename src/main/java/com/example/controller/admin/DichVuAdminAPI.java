package com.example.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.DichVu;

import com.example.service.DichVuService;

@RestController
@RequestMapping("/api/admin/dichvu")
public class DichVuAdminAPI {
    @Autowired
    private DichVuService dichVuService;

    // Đường dẫn lưu file ảnh
    private final String uploadDir = "C:/TN/Workspace/MotorBike/motorcycle_maintenance_management/src/main/resources/static/images/dich_vu/";
    // String uploadDir = "src/main/resources/static/images/";

    // 1. Lấy tất cả DichVu
    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public List<DichVu> getAllDichVu() {
        return dichVuService.findAll();
    }

    // 2. Lấy 1 DichVu theo ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public DichVu getOne(@PathVariable String id) {
        return dichVuService.findById(id);
    }

    // 3. Thêm mới DichVu (kèm file)
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('Admin')")
    public DichVu create(@RequestPart("dichVu") DichVu dv,
            @RequestPart("file") MultipartFile file) throws IOException {
        // Xử lý lưu file vào thư mục images
        if (!file.isEmpty()) {
            File uploadFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(uploadFile);
            dv.setHinhAnh(file.getOriginalFilename());
        }
        // Lưu DichVu và trả về đối tượng đã được lưu
        return dichVuService.create(dv);
    }

    // 4a. Cập nhật dịch vụ KHÔNG kèm file
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public DichVu update(@PathVariable String id, @RequestBody DichVu dv) {
        dv.setIdDichVu(id);
        dichVuService.update(dv);
        return dv;
    }

    // 4b. Cập nhật dịch vụ CÓ kèm file
    @PutMapping("/updateWithFile/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public DichVu updateWithFile(
            @PathVariable String id,
            @RequestPart("dichVu") DichVu dv,
            @RequestPart("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile);
                dv.setHinhAnh(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi upload file", e);
            }
        } else {
            // Nếu không có file mới, giữ nguyên ảnh cũ
            DichVu existing = dichVuService.findById(id);
            dv.setHinhAnh(existing.getHinhAnh());
        }

        dv.setIdDichVu(id);
        dichVuService.update(dv);
        return dv;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void delete(@PathVariable String id) {
        dichVuService.deleteById(id);
    }
}
