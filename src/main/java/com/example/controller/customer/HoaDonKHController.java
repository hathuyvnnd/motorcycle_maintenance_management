package com.example.controller.customer;

import java.util.List;

import com.example.model.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping  ("/api/khachhang")
public class HoaDonKHController {
    @Autowired
    HoaDonService hdService;

    @Autowired
    KhachHangService khService;

    @Autowired
    PhieuDichVuService pdvService;

    @Autowired
    PhieuDichVuCTService pdvctService;

    @Autowired
    TaiKhoanService taiKhoanService;

    // API lấy tất cả hóa đơn
    // @GetMapping("/api/hoadon")
    // public List<HoaDon> getAll() {
    //     return hdService.findAll();
    // }

    // API lấy hóa đơn theo ID khách hàng
    @GetMapping("/lichsu")
    public List<HoaDon> getHoaDonByKH() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("ID lấy từ SecurityContextHolder: " + id);
        TaiKhoan tk = taiKhoanService.findByIdTaiKhoan(id);
        System.out.println("ID Tài Khoản: " + id);
        KhachHang kh = khService.getByTaiKhoan(tk);

        System.out.println("Tài  Khoản Khách hàng: " + kh.getIdKhachHang());
        if (kh == null) {
            System.out.println("Không Tìm thấy KH");
            return null;  // Trả về null nếu không tìm thấy khách hàng
        }
        return hdService.hoaDonByKh(kh);
    }


    @GetMapping("/hoadonct")
    public List<PhieuDichVuCT> getPhieuDichVuByHD(@RequestParam("idHoaDon") String idHoaDon){
        HoaDon hd = hdService.findById(idHoaDon);
        if(hd != null){
            List<PhieuDichVu> pdvList = pdvService.findByHoaDon(hd);
            System.out.println(hd.getIdHoaDon());
            if (pdvList != null && !pdvList.isEmpty()) {
                PhieuDichVu pdv = pdvList.get(0);
                System.out.println(pdv.getIdPhieuDichVu());
                return pdvctService.getPhieuDichVuCTByPDV(pdv);
        }
        
    }
    return null;

}
@GetMapping("/hoadon")
    public HoaDon getHDByID(@RequestParam("idHoaDon") String idHoaDon){
        return hdService.getHoaDonById(idHoaDon);
    }
}
