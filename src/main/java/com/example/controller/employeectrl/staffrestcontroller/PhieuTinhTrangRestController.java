package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dto.reponse.ApiReponse;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service_impl.PhieuTinhTrangServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/phieu-tinh-trang")
@CrossOrigin("*")
@RestController
public class PhieuTinhTrangRestController {
    PhieuTinhTrangServiceImpl service;
    @PostMapping
    public ApiReponse<PhieuGhiNhanTinhTrangXe> taoPhieuTinhTrang(@RequestBody PhieuGhiNhanTinhTrangXe request) {
        ApiReponse<PhieuGhiNhanTinhTrangXe> response = new ApiReponse<>();
        try {
            PhieuGhiNhanTinhTrangXe ph = PhieuGhiNhanTinhTrangXe.builder()
                    .idPhieuGNX(service.generateNewId())
                    .moTaTinhTrangXe(request.getMoTaTinhTrangXe())
                    .ngayNhan(new Date())
                    .nhanVien(null)
                    .bienSoXe(request.getBienSoXe())
                    .build();
            response.setResult(service.create(ph));

            return response;
        } catch (Exception e) {
            response.setMessage("Khong the luu phieu tinh trang");
            return response;
        }
    }
}
