package com.example.controller.employeectrl.staffrestcontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.example.model.NhanVien;
import com.example.service_impl.NhanVienServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nhanvien/testupload")
@RequiredArgsConstructor
public class TestUploadController {

    private final NhanVienServiceImpl nhanVienService;

    // ✅ API: Get nhân viên theo id (dùng để lấy ảnh hiện tại)
    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable String id) {
        NhanVien nhanVien = nhanVienService.findById(id);
        if (nhanVien == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nhanVien);
    }

    // ✅ API: Upload avatar
    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable String id,
                                               @RequestParam("file") MultipartFile file) {
        try {
            NhanVien nhanVien = nhanVienService.findById(id);
            if (nhanVien == null) {
                return ResponseEntity.notFound().build();
            }

            // Thư mục chứa ảnh
            String uploadDir = "src/main/resources/static/images/";
            Files.createDirectories(Paths.get(uploadDir));

            // Đặt tên file random tránh trùng  UUID.randomUUID() + "_" +
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Update DB
            nhanVien.setHinhAnh(fileName);
            nhanVienService.update(nhanVien);


            return ResponseEntity.ok(Map.of(
                "message", "Upload thành công",
                "fileName", fileName
        ));
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Lỗi khi upload file"));
    }
    }
}
