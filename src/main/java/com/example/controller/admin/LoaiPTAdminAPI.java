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

import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;
import com.example.service.LoaiPhuTungService;

@RestController
@RequestMapping("/api/admin/loaiphutung")
public class LoaiPTAdminAPI {
    @Autowired
    private LoaiPhuTungService loaiPhuTungService;

    // 1. Lấy tất cả LoaiPhuTung
    @GetMapping
    public List<LoaiPhuTung> getAllLoaiPT() {
        return loaiPhuTungService.findAll();
    }

    // 2. Lấy 1 LoaiPhuTung theo ID
    @GetMapping("/{id}")
    public LoaiPhuTung getOne(@PathVariable String id) {
        return loaiPhuTungService.findById(id);
    }

    // 3. Thêm mới LoaiPhuTung
    @PostMapping("/add")
    public LoaiPhuTung create(@RequestBody LoaiPhuTung loaiPhuTung) {
        return loaiPhuTungService.create(loaiPhuTung);
    }

    // 4a. Cập nhật LoaiPhuTung
    @PutMapping("/{id}")
    public LoaiPhuTung update(@PathVariable String id, @RequestBody LoaiPhuTung loaiPhuTung) {
        loaiPhuTung.setIdLoaiPT(id);
        loaiPhuTungService.update(loaiPhuTung);
        return loaiPhuTung;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        loaiPhuTungService.deleteById(id);
    }
}
