package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dao.LichHenDao;
import com.example.dto.reponse.ApiReponse;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.dto.request.tinhtrangxe.UpdateTinhTrangRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.model.LichHen;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service_impl.LichHenServiceImpl;
import com.example.service_impl.PhieuTinhTrangServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/phieu-tinh-trang")
@CrossOrigin("*")
@RestController
public class PhieuTinhTrangRestController {
    PhieuTinhTrangServiceImpl service;
    LichHenServiceImpl lichHenService;
    LichHenDao lichHenService1;
    @PostMapping
    public ApiReponse<PhieuGhiNhanTinhTrangXe> taoPhieuTinhTrang(@RequestBody CreateTinhTrangXeRequest request) {
        ApiReponse<PhieuGhiNhanTinhTrangXe> response = new ApiReponse<>();
        try {
            System.out.println("Dữ liệu nhận từ client: " + request);
            System.out.println("id tu sinh: "+ service.generateNewId());
            CreateTinhTrangXeRequest ph = CreateTinhTrangXeRequest.builder()
                    .idPhieuGNX(service.generateNewId())
                    .moTaTinhTrangXe(request.getMoTaTinhTrangXe())
                    .ngayNhan(new Date())
                    .idNhanVien("NV010")
                    .idLichHen(request.getIdLichHen())
                    .build();
            System.out.println("a:  "+ ph);
            System.out.println("Phiếu tình trạng mới: " + ph);
            PhieuGhiNhanTinhTrangXe newPhieu = service.createPhieuGhiNhanTinhTrangXeRequest(ph);
            lichHenService.updateLichHenTrangThai(request.getIdLichHen());
            response.setResult(newPhieu);
            return response;
        } catch (Exception e) {
            response.setMessage("Khong the luu phieu tinh trang");
            System.out.println("Khong the luu phieu tinh trang");
            return response;
        }
    }
    @GetMapping("/phieu-gnx/find")
    public ApiReponse<PhieuGhiNhanTinhTrangXe> getPhieuGNXByIdLichHen(@RequestParam String idLichHen) {
        PhieuGhiNhanTinhTrangXe pgn = service.findPhieuByLichHen(idLichHen);
        ApiReponse<PhieuGhiNhanTinhTrangXe> reponse = new ApiReponse<>();
        reponse.setResult(pgn);
        return reponse;
    }
    @GetMapping("/getall")
    public ApiReponse<List<PhieuGhiNhanTinhTrangXe>> getAllPhieuTinhTrang() {
        List<PhieuGhiNhanTinhTrangXe> pgn = service.findAll();
        ApiReponse<List<PhieuGhiNhanTinhTrangXe>> reponse = new ApiReponse<>();
        reponse.setResult(pgn);
        return reponse;
    }
    @GetMapping("/id")
    public ApiReponse<PhieuGhiNhanTinhTrangXe> getPhieuTinhTrang(@RequestParam String idPhieuGNX) {
        PhieuGhiNhanTinhTrangXe pgn = service.findById(idPhieuGNX);
        ApiReponse<PhieuGhiNhanTinhTrangXe> reponse = new ApiReponse<>();
        reponse.setResult(pgn);
        return reponse;
    }
    @GetMapping("/search")
    public ApiReponse<List<PhieuGhiNhanTinhTrangXe>> searchPhieuTinhTrangByBienSoXe(@RequestParam String keyword) {
        List<PhieuGhiNhanTinhTrangXe> pgn = service.searchByBienSoXeKeyword(keyword);
        ApiReponse<List<PhieuGhiNhanTinhTrangXe>> reponse = new ApiReponse<>();
        reponse.setResult(pgn);
        return reponse;
    }





    @GetMapping("/test")
    ApiReponse<LichHen> testli(@RequestParam String idLichHen){
        LichHen lh = lichHenService1.findById(idLichHen).orElseThrow(() -> new AppException(ErrorCode.LICHHEN_NOTFOUND));
       ApiReponse<LichHen> response = new ApiReponse<>();
       if (lh != null){
       response.setResult(lh);
       }else{
           response.setMessage("khong thay lich hen");
       }

       return response;
    }


    @GetMapping("/testday")
    ApiReponse<List<LichHen>> testli1(){
        Date dt = new Date();
        List<LichHen> lh = lichHenService1.findByThoiGian(dt);
        ApiReponse<List<LichHen>> response = new ApiReponse<>();
        if (lh != null){
            response.setResult(lh);
        }else{
            response.setMessage("khong thay lich hen");
        }

        return response;
    }
      @PutMapping
        ApiReponse<PhieuGhiNhanTinhTrangXe> updatePhieuGNX(@RequestBody UpdateTinhTrangRequest request){
            ApiReponse<PhieuGhiNhanTinhTrangXe> apiReponse = new ApiReponse<>();
            apiReponse.setResult(service.updateGhiChu(request));
            return apiReponse;
        }
}
