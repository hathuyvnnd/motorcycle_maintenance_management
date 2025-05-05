package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dao.HoaDonDao;
import com.example.dao.PhieuDichVuCTDao;
import com.example.dao.PhieuSuDungPhuTungCTDao;
import com.example.dto.reponse.ApiReponse;
import com.example.model.*;
import com.example.service_impl.HoaDonServiceImpl;
import com.example.service_impl.LichHenServiceImpl;
import com.example.service_impl.NhanVienServiceImpl;
import com.example.service_impl.PhieuDichVuCTServiceImpl;
import com.example.service_impl.PhieuDichVuServiceImpl;
import com.example.service_impl.PhieuTinhTrangServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/test/findid")
public class TestFindByIdRest {
    LichHenServiceImpl lhsv;
    PhieuTinhTrangServiceImpl pttsv;
    PhieuDichVuServiceImpl pdvsv;
    PhieuSuDungPhuTungCTDao phieuSuDungPhuTungCTDao;
    PhieuDichVuCTDao phieuDichVuCTDao;
    HoaDonServiceImpl hdsv;
    HoaDonDao hddao;
    NhanVienServiceImpl nvsv;
    @GetMapping("/nhan-vien")
    ApiReponse<NhanVien> findNhanVien(@RequestParam String id){
        ApiReponse<NhanVien> reponse = new ApiReponse<>();
        reponse.setResult(nvsv.findById(id));
        return reponse;
    }
    @GetMapping("/lich-hen")
    ApiReponse<LichHen> findLichHen(@RequestParam String id){
        ApiReponse<LichHen> reponse = new ApiReponse<>();
        reponse.setResult(lhsv.findById(id));
        return reponse;
    }
    @GetMapping("/phieu-tinh-trang")
    ApiReponse<PhieuGhiNhanTinhTrangXe> findTinhTrang(@RequestParam String id){
        ApiReponse<PhieuGhiNhanTinhTrangXe> reponse = new ApiReponse<>();
        reponse.setResult(pttsv.findById(id));
        return reponse;
    }
    @GetMapping("/phieu-tinh-trang-by-lich-hen")
    ApiReponse<PhieuGhiNhanTinhTrangXe> findTinhTrangByLichHen(@RequestParam String id){
        ApiReponse<PhieuGhiNhanTinhTrangXe> reponse = new ApiReponse<>();
        reponse.setResult(pttsv.findPhieuByLichHen(id));
        return reponse;
    }
    @GetMapping("/phieu-dich-vu")
    ApiReponse<PhieuDichVu> findDichVu(@RequestParam String id){
        ApiReponse<PhieuDichVu> reponse = new ApiReponse<>();
        reponse.setResult(pdvsv.findById(id));
        return reponse;
    }
    @GetMapping("/hoa-don")
    ApiReponse<HoaDon> findHoaDonHoaDon(@RequestParam String id){
        ApiReponse<HoaDon> reponse = new ApiReponse<>();
        reponse.setResult(hdsv.findById(id));
        return reponse;
    }
    @GetMapping("/hoa-don/by-lich-hen")
    ApiReponse<HoaDon> findHoaDonByLichHen(@RequestParam String id){
        ApiReponse<HoaDon> reponse = new ApiReponse<>();
        reponse.setResult(hddao.findByPhieuDichVuHD_PhieuGNX_LichHen_IdLichHen(id));
        return reponse;
    }

    @GetMapping("/phieu-phu-tung")
    ApiReponse<List<PhieuSuDungPhuTungCT>> findPhieuDungPhuTung(@RequestParam String id){
        ApiReponse<List<PhieuSuDungPhuTungCT>> reponse = new ApiReponse<>();
        reponse.setResult(phieuSuDungPhuTungCTDao.findAllByLichHenId(id));
        return reponse;
    }
    @GetMapping("/phieu-dich-vu-ct")
    ApiReponse<List<PhieuDichVuCT>> findPhieuDungDichVuCT(@RequestParam String id){
        ApiReponse<List<PhieuDichVuCT>> reponse = new ApiReponse<>();
        reponse.setResult(phieuDichVuCTDao.findDichVuByLichHenId(id));
        return reponse;
    }
   
    
}
