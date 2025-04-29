package com.example.controller.customer;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;
import com.example.service.LoaiPhuTungService;
import com.example.service.PhuTungService;

@RestController
@RequestMapping  ("/api/khachhang")
public class PhuTungKHRest {
    @Autowired
    PhuTungService pTungService;

    @Autowired
    LoaiPhuTungService loaiPTService;

    @GetMapping("/phutung")
    public List<PhuTung> getPhuTungTheoLoai(@RequestParam("idLoaiPT") String idLoaiPT) {
    System.out.println(" Lấy danh sách phụ tùng cho loại: " + idLoaiPT);
    LoaiPhuTung loaiPT = loaiPTService.findById(idLoaiPT);
    if (loaiPT == null) {
        System.out.println(" Không tìm thấy loại phụ tùng với ID: " + idLoaiPT);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loại phụ tùng không tồn tại");
    }
    return pTungService.findByLoaiPT(loaiPT);
}
    
}
