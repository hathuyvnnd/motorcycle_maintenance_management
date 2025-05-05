package com.example.controller.customer;

import com.example.dto.reponse.MessageResponse;
import com.example.dto.request.khachhang.LichHenDTO;
import com.example.model.KhachHang;
import com.example.model.LichHen;
import com.example.model.TaiKhoan;
import com.example.service.KhachHangService;
import com.example.service.LichHenService;
import com.example.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping  ("/api/khachhang")
public class LichHenKHController {
    @Autowired
    LichHenService lhService;
    @Autowired
    KhachHangService khService;
    @Autowired
    TaiKhoanService tkService;

    @PostMapping("/lichhen")
    public ResponseEntity<?> datLich(@RequestBody LichHenDTO dto) {
        String idTaiKhoan = SecurityContextHolder.getContext().getAuthentication().getName();
        TaiKhoan tk = tkService.findByIdTaiKhoan(idTaiKhoan);
        KhachHang kh = khService.getByTaiKhoan(tk);
        dto.setIdKhachHang(kh.getIdKhachHang());
        lhService.taoLichHenByKH(dto);
        return ResponseEntity.ok(new MessageResponse(" Đặt lịch thành công!"));
    }
    @GetMapping("/dslichhen")
    public List<LichHen> getLichHenByKh(){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("ID lấy từ SecurityContextHolder: " + id);
        TaiKhoan tk = tkService.findByIdTaiKhoan(id);
        System.out.println("ID Tài Khoản: " + id);
        KhachHang kh = khService.getByTaiKhoan(tk);
        System.out.println("Tài  Khoản Khách hàng: " + kh.getIdKhachHang());
        if (kh == null) {
            System.out.println("Không Tìm thấy KH");
            return null;  // Trả về null nếu không tìm thấy khách hàng
        }
        return lhService.getLichHenByKh(kh);
    }

}
