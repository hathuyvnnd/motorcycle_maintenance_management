package com.example.controller.employeectrl.staffrestcontroller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.PhieuDichVuDAO;
import com.example.dto.reponse.ApiReponse;
import com.example.dto.reponse.LichHenResponse;
import com.example.dto.request.dichvu.PhieuDichVuCreateRequest;
import com.example.dto.request.hoadon.HoaDonRequest;
import com.example.dto.request.phutung.PhuTungRequest;
import com.example.model.DichVu;
import com.example.model.HoaDon;
import com.example.model.LichHen;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;
import com.example.model.PhieuSuDungPhuTungCT;
import com.example.model.PhuTung;
import com.example.service_impl.HoaDonServiceImpl;
import com.example.service_impl.LichHenServiceImpl;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/hoa-don")
@CrossOrigin("*")
@RestController
public class HoaDonRestController {
    HoaDonServiceImpl service;
    LichHenServiceImpl lichHenService;
 
 @PostMapping("/tao-phieu")
 public ApiReponse<?> taoPhieu(@RequestBody HoaDonRequest request) {
  ApiReponse<Object> response = new ApiReponse<>();
  System.out.println("Kiem tra requse:" + request);
  String newidHD = service.generateNewId();
  HoaDonRequest cpdv = HoaDonRequest.builder()
          .idHoaDon(newidHD)
          .idPhieuDichVu(request.getIdPhieuDichVu())
          .idNhanVienTao(request.getIdNhanVienTao())
          .ngayTao(new Date())
          .trangThaiThanhToan(false)
          .tongTien(request.getTongTien())
          .phuongThucThanhToan(request.getPhuongThucThanhToan())
          .idKhachHang(request.getIdKhachHang())
          .build();
  System.out.println("aa " + cpdv);
  service.createHoaDonRequest(cpdv);
 
    lichHenService.updateLichHenTrangThai(request.getIdLichHen());
    return response;
}
 
}
