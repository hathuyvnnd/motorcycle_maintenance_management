package com.example.controller.customer;

import com.example.dto.reponse.MessageResponse;
import com.example.dto.request.khachhang.LichHenDTO;
import com.example.service.LichHenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping  ("/api/khachhang")
public class LichHenKHController {
    @Autowired
    LichHenService lhService;

    @PostMapping("/lichhen")
    public ResponseEntity<?> datLich(@RequestBody LichHenDTO dto) {
        lhService.taoLichHenByKH(dto);
        return ResponseEntity.ok(new MessageResponse(" Đặt lịch thành công!"));
    }
}
