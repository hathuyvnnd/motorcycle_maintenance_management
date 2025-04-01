package com.example.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.HoaDon;
import com.example.model.KhachHang;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;
import com.example.service.HoaDonService;
import com.example.service.KhachHangService;
import com.example.service.PhieuDichVuCTService;
import com.example.service.PhieuDichVuService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping
public class HoaDonKHController {
    @Autowired
    HoaDonService hdService;

    @Autowired
    KhachHangService khService;

    @Autowired
    PhieuDichVuService pdvService;

    @Autowired
    PhieuDichVuCTService pdvctService;

    // API lấy tất cả hóa đơn
    // @GetMapping("/api/hoadon")
    // public List<HoaDon> getAll() {
    //     return hdService.findAll();
    // }

    // API lấy hóa đơn theo ID khách hàng
    @GetMapping("/api/hoadonOne")
    public List<HoaDon> getHoaDonByKH(@RequestParam("id") String id) {
        KhachHang kh = khService.findById(id);
        if (kh == null) {
            System.out.println("Không Tìm thấy KH");
            return null;  // Trả về null nếu không tìm thấy khách hàng
        }
        return hdService.hoaDonByKh(kh);
    }


    @GetMapping("/api/hoadonct")
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
@GetMapping("api/hoadon")
    public HoaDon getHDByID(@RequestParam("idHoaDon") String idHoaDon){
        return hdService.getHoaDonById(idHoaDon);
    }
}
