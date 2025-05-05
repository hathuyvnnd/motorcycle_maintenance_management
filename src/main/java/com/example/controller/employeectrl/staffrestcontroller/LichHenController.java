package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dao.KhachHangDao;
import com.example.dao.TaiKhoanDao;
import com.example.dto.reponse.ApiReponse;
import com.example.dto.reponse.LichHenResponse;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.dto.request.lichhen.UpdateTrangThaiLichHenRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.LichHenMapper;
import com.example.model.*;
import com.example.service_impl.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/lich-hen")
@CrossOrigin("*")
@RestController
public class LichHenController {

     LichHenServiceImpl lichHenService;
    TaiKhoanServiceImpl taiKhoanDAO;
    KhachHangServiceImpl khachHangDAO;
    DichVuServiceImpl dichVuService;
    LichHenCTServiceImpl lichHenCTService;

    TaiKhoanDao taiKhoanDAO1;
    // KhachHangDao khachHangDAO1;

     LichHenMapper mapper;
    // Lấy tất cả lịch hẹn
        @GetMapping
        ApiReponse<List<LichHenResponse>> getAllLichHen() {
            List<LichHen> lslh = lichHenService.findAll();
            List<LichHenResponse> lhrp = mapper.toLichHenResponse(lslh);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();

            apiReponse.setResult(lhrp);
            return apiReponse;
        }
    @GetMapping("/testallnorp")
    ApiReponse<List<LichHen>> getAllLichHen1() {
        List<LichHen> lslh = lichHenService.findAll();
        ApiReponse<List<LichHen>> apiReponse = new ApiReponse<>();
        apiReponse.setResult(lslh);
        return apiReponse;
    }

  

    @GetMapping("/today")
    ApiReponse<List<LichHenResponse>> getLichHenToday() {
        List<LichHen> lslh = lichHenService.getLichHenToday();

        // Lọc danh sách, loại bỏ những lịch hẹn có trạng thái "Chờ xác nhận"
        List<LichHen> filteredLichHen = lslh.stream()
                .filter(lichHen -> !"Chờ xác nhận".equals(lichHen.getTrangThai()))
                .collect(Collectors.toList());

        List<LichHenResponse> lhrp = mapper.toLichHenResponse(filteredLichHen);

        ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
        apiReponse.setResult(lhrp);
        return apiReponse;
    }


    @GetMapping("/search")
        public ApiReponse<List<LichHenResponse>> searchLichHenByBienSoXe(@RequestParam String bienSoXe) {
            List<LichHen> result = lichHenService.searchLichHenByBienSo(bienSoXe);
            System.out.println("testt: "+result);
            List<LichHenResponse> lhrp = mapper.toLichHenResponse(result);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lhrp);
            return apiReponse;
        }

        @PostMapping
        ApiReponse<LichHen> createLH(@RequestBody @Valid LichHenCreateRequest request){
            ApiReponse<LichHen> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lichHenService.createLichHenRequest(request));
            return apiReponse;
        }
        @PutMapping("/{id}")
        ApiReponse<LichHen> updateLH(@PathVariable String id,@RequestBody LichHenUpdateRequest request){
            ApiReponse<LichHen> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lichHenService.updateLichHenRequest(id, request));
            return apiReponse;
        }

    @GetMapping("/checkPhone")
    public ApiReponse<?> checkSoDienThoai(@RequestParam String phone) {
        ApiReponse<Object> response = new ApiReponse<>();

        // Kiểm tra số điện thoại có trong hệ thống không
        TaiKhoan taiKhoan = taiKhoanDAO.findById(phone);
        if (taiKhoan != null) {
            // Nếu có tài khoản, lấy thông tin khách hàng
            KhachHang khachHang = khachHangDAO.findByTaiKhoanKH(taiKhoan);
            if (khachHang != null) {
                response.setResult(Map.of("exists", true, "tenKhachHang", khachHang.getHoTen()));
                return response;
            }
        }

        // Nếu không có tài khoản, trả về null để tạo mới
        response.setResult(null);
        response.setMessage("Chưa có tài khoản");
        return response;
    }
    @PostMapping("/tao-lich-hen")
    public ApiReponse<?> taoLichHen(@RequestBody LichHenCreateRequest request) {
        ApiReponse<Object> response = new ApiReponse<>();
        // Kiểm tra khách hàng dựa vào số điện thoại
        TaiKhoan taiKhoan = taiKhoanDAO1.findById(request.getIdKhachHang()).orElseGet(() -> {
            // Nếu không tìm thấy, tạo mới tài khoản
            TaiKhoanKhachHang newTaiKhoan = TaiKhoanKhachHang.builder()
                    .idTaiKhoan(request.getIdKhachHang())
                    .matKhau("123")
                    .trangThai(true)
                    .build();
            taiKhoanDAO1.save(newTaiKhoan);
            System.out.println("ab: " + newTaiKhoan.getVaiTro());
            KhachHang newKhachHang = KhachHang.builder()
                    // .idKhachHang(khachHangDAO.generateNewId())
                    .taiKhoanKH(newTaiKhoan)
                    .hoTen(request.getTenKhachHang())
                    .build();
            khachHangDAO.create(newKhachHang);
            response.setResult(newKhachHang);
            return newTaiKhoan;
        });
        String newidLH = lichHenService.generateNewId();
        LichHenCreateRequest lhrp = LichHenCreateRequest.builder()
                .idLichHen(newidLH)
                .idKhachHang(khachHangDAO.findIdKhachHangByTaiKhoanKH_IdTaiKhoan(request.getIdKhachHang()))
                .thoiGian(request.getThoiGian())
                .trangThai("Đã xác nhận")
                .bienSoXe(request.getBienSoXe())
                .ghiChu(request.getGhiChu())
                .build();
        System.out.println("aa " + lhrp);
        lichHenService.createLichHenRequest(lhrp);
        System.out.println("Ten:  " + request.getTenKhachHang());
        System.out.println("id tu sinh:  " + newidLH);
        LichHen lhdv = lichHenService.findById(newidLH);
        System.out.println("lhdv: " + lhdv.getIdLichHen());
        System.out.println("id tu sinh:  " + newidLH);
        for (String idDichVu : request.getListIdDichVu()) {
            DichVu dichVu = dichVuService.findById(idDichVu);
            LichHenCT lichHenCT = LichHenCT.builder()
                    .idLichHenCT(lichHenCTService.generateNewId())
                    .idLichHen(lhdv)
                    .ghiChu(request.getGhiChu())
                    .idDichVu(dichVuService.findById(idDichVu))
                    .build();
            // Lưu vào DB
            lichHenCTService.create(lichHenCT);
        }
        return response;
    }

//
    @GetMapping("/checkk")
    ApiReponse<KhachHang>  checkFindKH(@RequestParam String phone) {
            KhachHang kh = khachHangDAO.findByTaiKhoanKH(taiKhoanDAO.findById(phone));
            ApiReponse<KhachHang> rp = new ApiReponse<>();
            rp.setResult(kh);
            return rp;
    }
    @GetMapping("/checkk1")
    ApiReponse<KhachHang>  checkFindKH1(@RequestParam String phone) {
        KhachHang kh = khachHangDAO.findById(phone);
        ApiReponse<KhachHang> rp = new ApiReponse<>();
        rp.setResult(kh);
        return rp;
    }

    @PutMapping("/update-trang-thai")
    public ApiReponse<?> updateTrangThai(@RequestBody UpdateTrangThaiLichHenRequest request) {
        ApiReponse<?> rp = new ApiReponse<>();
        Boolean blud = lichHenService.updateTrangThai(request.getIdLichHen(), request.getTrangThai());
        if(blud){
            rp.setMessage("Update trạng thái lịch hẹn thành công");
        }else{
            rp.setMessage("Update trạng thái thất bại");
        }
        return rp;
    }




    @GetMapping("/cho-xac-nhan")
    ApiReponse<List<LichHenResponse>> getLichHenChoXacNhan() {
        List<LichHen> lslh = lichHenService.getLichHenChoXacNhan();
        List<LichHenResponse> lhrp = mapper.toLichHenResponse(lslh);
        ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
        apiReponse.setResult(lhrp);
        return apiReponse;
    }

    @GetMapping("/chua-hoan-tat")
    ApiReponse<List<LichHenResponse>> getLichHenChuaHoanTat() {
        List<LichHen> lslh = lichHenService.layTatCaLichHenNgoaiTrangThaiChinh();
        List<LichHenResponse> lhrp = mapper.toLichHenResponse(lslh);
        ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
        apiReponse.setResult(lhrp);
        return apiReponse;
    }
       @PostMapping("/update-ngay")
    public ApiReponse<?> updateNgay(@RequestBody LichHen request) {
        Boolean blud =  lichHenService.updateNgay(request.getIdLichHen(),request.getThoiGian());
        ApiReponse<?> rp = new ApiReponse<>();
        if(blud){
            rp.setMessage("Update trạng thái lịch hẹn thành công");
        }else{
            rp.setMessage("Update trạng thái thất bại");
        }
        return rp;
    }

}
