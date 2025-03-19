package com.example.controller;

import com.example.model.NhanVien;
import com.example.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping
    public List<NhanVien> getAllNhanVien() {
        return nhanVienService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable String id) {
        NhanVien nhanVien = nhanVienService.findById(id);
        if (nhanVien != null) {
            return ResponseEntity.ok(nhanVien);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public NhanVien createNhanVien(@RequestBody NhanVien nhanVien) {
        return nhanVienService.create(nhanVien);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<NhanVien> updateNhanVien(@PathVariable String id, @RequestBody NhanVien updatedNhanVien) {
//        NhanVien existingNhanVien = nhanVienService.findById(id);
//        if (existingNhanVien != null) {
//            updatedNhanVien.setIdNhanVien(id);
//            nhanVienService.update(updatedNhanVien);
//            return ResponseEntity.ok(updatedNhanVien);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable String id) {
        if (nhanVienService.exitsById(id)) {
            nhanVienService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
