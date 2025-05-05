package com.example.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.model.PhuTung;

import com.example.service.PhuTungService;

@RestController
@RequestMapping("/api/admin/phutung")
public class PhuTungAdminAPI {
    @Autowired
    private PhuTungService phuTungService;

    // Đường dẫn lưu file ảnh
    private final String uploadDir = "C:/TN/Workspace/MotorBike/motorcycle_maintenance_management/src/main/resources/static/images/phu_tung/";

    // 1. Lấy tất cả PhuTung
    @GetMapping
    public List<PhuTung> getAllPhuTung() {
        return phuTungService.findAll();
    }

    // 2. Lấy 1 PhuTung theo ID
    @GetMapping("/{id}")
    public PhuTung getOne(@PathVariable String id) {
        return phuTungService.findById(id);
    }

    // 3. Thêm mới PhuTung (kèm file)
    @PostMapping("/upload")
    public PhuTung create(@RequestPart("phuTung") PhuTung pt,
            @RequestPart("file") MultipartFile file) throws IOException {
        // Xử lý lưu file vào thư mục images
        if (!file.isEmpty()) {
            File uploadFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(uploadFile);
            pt.setHinhAnh(file.getOriginalFilename());
        }
        // Lưu DichVu và trả về đối tượng đã được lưu
        return phuTungService.create(pt);
    }

    // 4a. Cập nhật PhuTung KHÔNG kèm file
    @PutMapping("/{id}")
    public PhuTung update(@PathVariable String id, @RequestBody PhuTung pt) {
        pt.setIdPhuTung(id);
        phuTungService.update(pt);
        return pt;
    }

    // 4b. Cập nhật PhuTung CÓ kèm file
    @PutMapping("/updateWithFile/{id}")
    public PhuTung updateWithFile(
            @PathVariable String id,
            @RequestPart("phuTung") PhuTung pt,
            @RequestPart("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                File uploadFile = new File(uploadDir + file.getOriginalFilename());
                file.transferTo(uploadFile);
                pt.setHinhAnh(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi upload file", e);
            }
        } else {
            // Nếu không có file mới, giữ nguyên ảnh cũ
            PhuTung existing = phuTungService.findById(id);
            pt.setHinhAnh(existing.getHinhAnh());
        }

        pt.setIdPhuTung(id);
        phuTungService.update(pt);
        return pt;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        phuTungService.deleteById(id);
    }
}
