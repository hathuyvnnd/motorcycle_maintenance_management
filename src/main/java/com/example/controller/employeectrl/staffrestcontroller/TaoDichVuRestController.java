package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dto.reponse.ApiReponse;
import com.example.dto.request.phieudichvu.PhieuDichVuRequest;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service_impl.DichVuServiceImpl;
import com.example.service_impl.PhieuDichVuServiceImpl;
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
 @PostMapping
 public ApiReponse<PhieuGhiNhanTinhTrangXe> taoPhieuTinhTrang(@RequestBody PhieuDichVuRequest request) {
  ApiReponse<PhieuGhiNhanTinhTrangXe> response = new ApiReponse<>();
  try {
   System.out.println("Dữ liệu nhận từ client: " + request);
   System.out.println("id tu sinh: "+ service.generateNewId());
    PhieuDichVuRequest ph = PhieuDichVuRequest.builder()
           .idPhieuDichVu(service.generateNewId())
           .idPhieuGNX(request.getIdPhieuGNX())
           .ngayThucHien(new Date())
           .idNhanVienTaoPhieu("NV010")
           .tenNhanVienSuaChua(request.getTenNhanVienSuaChua())
            .trangThaiSuaChua(false)
           .build();
   System.out.println("a:  "+ ph);
   System.out.println("Phieu dich vu mới: " + ph);

   PhieuGhiNhanTinhTrangXe newPhieu = service.createPhieuGhiNhanTinhTrangXeRequest(ph);
   lichHenService.updateLichHenTrangThai(request.getBienSoXe());
   response.setResult(newPhieu);

   return response;
  } catch (Exception e) {
   response.setMessage("Khong the luu phieu tinh trang");
   System.out.println("Khong the luu phieu tinh trang");
   return response;
  }
 }

}
