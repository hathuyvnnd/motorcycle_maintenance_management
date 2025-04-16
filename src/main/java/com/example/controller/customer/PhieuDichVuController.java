package com.example.controller.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.HoaDon;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;
import com.example.model.PhieuSuDungPhuTungCT;
import com.example.service.HoaDonService;
import com.example.service.PhieuDichVuCTService;
import com.example.service.PhieuDichVuService;
import com.example.service.PhieuSuDungPhuTungCTService;

@RestController
@RequestMapping
public class PhieuDichVuController {
    @Autowired
    PhieuDichVuService pdvService;

    @Autowired
    PhieuDichVuCTService pdvctService;

    @Autowired
    PhieuSuDungPhuTungCTService psdptService;
    @GetMapping("/api/lichsu")
    public List<PhieuDichVu> getListPDV() {
        return pdvService.getAllDichVus();
    }

    // @GetMapping("/api/lichsu")
    // public List<PhieuDichVu> getPDVByKH(@RequestParam("idKhachHang") String idKhachHang) {
    //     return pdvService.getListPDVByKh(idKhachHang);
    // }
    @GetMapping("/api/chitiet")
public ResponseEntity<?> getChiTietDV(@RequestParam("idPhieuDichVu") String idPhieuDichVu) {
    try {
        Map<String, Object> response = new HashMap<>();
        
        // Kiểm tra xem phiếu dịch vụ có tồn tại không
        PhieuDichVu pdv = pdvService.getByIdPDV(idPhieuDichVu);
        if (pdv == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy phiếu dịch vụ");
        }

        // Lấy danh sách chi tiết dịch vụ & phụ tùng
        List<PhieuDichVuCT> dsDichVuCT = pdvctService.getPhieuDichVuCTByPDV(pdv);
        List<PhieuSuDungPhuTungCT> dsPhuTungCT = psdptService.findByPDV(pdv);

        response.put("dichVuChiTiet", dsDichVuCT);
        response.put("phuTungChiTiet", dsPhuTungCT);

        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi server: " + e.getMessage());
    }
}

    
    
}
