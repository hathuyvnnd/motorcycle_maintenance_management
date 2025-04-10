package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dao.PhieuDichVuDAO;
import com.example.dto.reponse.ApiReponse;
import com.example.dto.request.dichvu.PhieuDichVuCreateRequest;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.phieudichvu.PhieuDichVuRequest;
import com.example.dto.request.phutung.PhuTungRequest;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.model.*;
import com.example.service.PhieuSuDungPhuTungCTService;
import com.example.service_impl.DichVuServiceImpl;
import com.example.service_impl.LichHenServiceImpl;
import com.example.service_impl.PhieuDichVuCTServiceImpl;
import com.example.service_impl.PhieuDichVuServiceImpl;
import com.example.service_impl.PhieuSuDungPTCTImpl;
import com.example.service_impl.PhuTungServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/phieu-dich-vu")
@CrossOrigin("*")
@RestController
public class TaoDichVuRestController {
 PhieuDichVuServiceImpl service;
 DichVuServiceImpl dichVuService;
 LichHenServiceImpl lichHenService;
 PhieuDichVuCTServiceImpl phieuDichVuCTService;
 PhuTungServiceImpl phuTungServiceImpl;
 PhieuSuDungPTCTImpl phieuSuDungPhuTungCTservice;
 @GetMapping("/phieu-dich-vu")
 ApiReponse<PhieuDichVu> findDichVu(@RequestParam String id){
  ApiReponse<PhieuDichVu> reponse = new ApiReponse<>();
  PhieuDichVu pdv = service.findById(id);
  reponse.setResult(pdv);
  return reponse;
 }

 @PostMapping("/tao-phieu")
 public ApiReponse<?> taoPhieu(@RequestBody PhieuDichVuCreateRequest request) {
  ApiReponse<Object> response = new ApiReponse<>();
  System.out.println("Kiem tra requse:" + request);
  String newidPDV = service.generateNewId();
  PhieuDichVuCreateRequest cpdv = PhieuDichVuCreateRequest.builder()
          .idPhieuDichVu(newidPDV)
          .idNhanVienTaoPhieu("NV001")
          .ngayThucHien(new Date())
          .trangThaiSuaChua(false)
          .tenNhanVienSuaChua(request.getTenNhanVienSuaChua())
          .idPhieuGNX(request.getIdPhieuGNX())
          .build();
  System.out.println("aa " + cpdv);
  service.createPhieuDichVuRequest(cpdv);
  System.out.println("id tu sinh:  " + newidPDV);
  PhieuDichVu pdv = service.findById(newidPDV);
  System.out.println("lhdv: " + pdv.getIdPhieuDichVu());
  System.out.println("id tu sinh:  " + newidPDV);
  for (String idDichVu : request.getListIdDichVu()) {
   DichVu dichVu = dichVuService.findById(idDichVu);
   System.out.println("id dich vu tung cai" + dichVu.getIdDichVu());
   PhieuDichVuCT phieuDichVuCT = PhieuDichVuCT.builder()
           .idPhieuDichVuCT(phieuDichVuCTService.generateNewId())
           .phieuDichVu(pdv)
           .giaDichVu(dichVu.getGiaDichVu())
           .dichVu(dichVu)
           .ngayThucHien(new Date())
           .build();
   // Lưu vào DB
   phieuDichVuCTService.create(phieuDichVuCT);
  }
 for (PhuTungRequest phuTungRequest : request.getDanhSachPhuTung()) {
        PhuTung phuTung = phuTungServiceImpl.findById(phuTungRequest.getId());
        System.out.println("id dich vu tung cai" + phuTung.getIdPhuTung());
        PhieuSuDungPhuTungCT phieuSuDungPhuTungCT = PhieuSuDungPhuTungCT.builder()
                .idPhieuSuDungPhuTungCT(phieuSuDungPhuTungCTservice.generateNewId())
                .soLuong(phuTungRequest.getSoLuong())
                .phuTung(phuTung)
                .phieuDichVu(pdv)
                .build();
        // Lưu vào DB
        phieuSuDungPhuTungCTservice.create(phieuSuDungPhuTungCT);
       }
lichHenService.updateLichHenTrangThai(request.getIdLichHen());
return response;
}
 PhieuDichVuDAO dao;
 @GetMapping("/phieu-dich-vu/id-lich-hen")
 ApiReponse<PhieuDichVu> findDichVuByIdLichHen(@RequestParam String id){
  ApiReponse<PhieuDichVu> reponse = new ApiReponse<>();
  PhieuDichVu pdv = dao.findByPhieuGNX_LichHen_IdLichHen(id);
  reponse.setResult(pdv);
  return reponse;
 }

}
