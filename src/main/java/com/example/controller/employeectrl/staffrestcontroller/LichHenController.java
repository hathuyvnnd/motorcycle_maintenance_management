package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dao.KhachHangDao;
import com.example.dao.TaiKhoanDao;
import com.example.dto.reponse.ApiReponse;
import com.example.dto.reponse.LichHenResponse;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.LichHenMapper;
import com.example.model.KhachHang;
import com.example.model.LichHen;
import com.example.model.TaiKhoan;
import com.example.model.TaiKhoanKhachHang;
import com.example.service_impl.KhachHangServiceImpl;
import com.example.service_impl.LichHenServiceImpl;
import com.example.service_impl.TaiKhoanServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/lich-hen")
@CrossOrigin("*")
@RestController
public class LichHenController {

     LichHenServiceImpl lichHenService;
    TaiKhoanServiceImpl taiKhoanDAO;
    KhachHangServiceImpl khachHangDAO;

    TaiKhoanDao taiKhoanDAO1;
    KhachHangDao khachHangDAO1;

     LichHenMapper mapper;
    // Lấy tất cả lịch hẹn
        @GetMapping
        ApiReponse<List<LichHenResponse>> getAllLichHen() {
            List<LichHen> lslh = lichHenService.findAll();
            List<LichHenResponse> lhrp = mapper.toLichHenReponse(lslh);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();

            apiReponse.setResult(lhrp);
            return apiReponse;
        }
        @GetMapping("/today")
        ApiReponse<List<LichHenResponse>> getLichHenToday() {
            List<LichHen> lslh = lichHenService.getLichHenToday();
            List<LichHenResponse> lhrp = mapper.toLichHenReponse(lslh);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lhrp);
            return apiReponse;
        }

        @GetMapping("/search")
        public ApiReponse<List<LichHenResponse>> searchLichHen(@RequestParam String bienSoXe) {
            List<LichHen> result = lichHenService.searchLichHenByBienSo(bienSoXe);
            System.out.println("testt: "+result);
            List<LichHenResponse> lhrp = mapper.toLichHenReponse(result);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lhrp);
            return apiReponse;
        }

        @PostMapping
        ApiReponse<LichHen> createUser(@RequestBody @Valid LichHenCreateRequest request){
            ApiReponse<LichHen> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lichHenService.createLichHenRequest(request));
            return apiReponse;
        }
        @PutMapping("/{id}")
        ApiReponse<LichHen> updateUser(@PathVariable String id,@RequestBody LichHenUpdateRequest request){
            ApiReponse<LichHen> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lichHenService.updateLichHenRequest(id, request));
            return apiReponse;
        }

    @GetMapping("/checkPhone")
    public ApiReponse<?> checkSoDienThoai(@RequestParam String phone) {
        ApiReponse<Object> response = new ApiReponse<>();

        // Kiểm tra số điện thoại có trong hệ thống không
        TaiKhoan taiKhoan = taiKhoanDAO1.findById(phone).orElse(null);
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
            System.out.println("ab: "+ newTaiKhoan.getVaiTro());
            KhachHang newKhachHang = KhachHang.builder()
                    .idKhachHang(khachHangDAO.generateNewId())
                    .taiKhoanKH(newTaiKhoan)
                    .hoTen(request.getTenKhachHang())
                    .build();
            khachHangDAO1.save(newKhachHang);
            response.setResult(khachHangDAO1.save(newKhachHang));
            return newTaiKhoan;
        });

        LichHenCreateRequest lhrp = LichHenCreateRequest.builder()
                .idLichHen(lichHenService.generateNewId())
                .idKhachHang(khachHangDAO.findIdKhachHangByTaiKhoanKH_IdTaiKhoan(request.getIdKhachHang()))
                .thoiGian(request.getThoiGian())
                .trangThai(true)
                .ghiChu(request.getGhiChu())
                .dichVu(request.getDichVu())
                .bienSoXe(request.getBienSoXe())
                .build();
        System.out.println("aa "+ lhrp);
        response.setResult(lichHenService.createLichHenRequest(lhrp));
        System.out.println("Ten:  " + request.getTenKhachHang());
        return response;
    }

//
//    @GetMapping("/check-phone")
//    public ApiReponse<?> checkPhone(@RequestParam String phone,@RequestParam(required = false) String tenKhachHang) {
//        ApiReponse<Object> response = new ApiReponse<>();
//
//        // Kiểm tra nếu phone là null hoặc rỗng
//        if (phone == null || phone.trim().isEmpty()) {
//            response.setMessage("Số điện thoại không hợp lệ.");
//            response.setResult(null);
//            return response;
//        }
//
//        TaiKhoan taiKhoan = taiKhoanDAO1.findById(phone).orElseGet(() -> {
//                    // Nếu không tìm thấy, tạo mới tài khoản
//            TaiKhoanKhachHang newTaiKhoan = TaiKhoanKhachHang.builder()
//                            .idTaiKhoan(phone)
//                            .matKhau("123")
//                            .trangThai(true)
//                            .build();
//                    taiKhoanDAO1.save(newTaiKhoan);
//            System.out.println("ab: "+ newTaiKhoan.getVaiTro());
//                KhachHang newKhachHang = KhachHang.builder()
//                        .idKhachHang(khachHangDAO.generateNewId())
//                        .hoTen(tenKhachHang) // Lấy họ tên từ giao diện
//                        .taiKhoanKH(newTaiKhoan)
//                        .build();
//                khachHangDAO1.save(newKhachHang);
//                response.setResult(khachHangDAO1.save(newKhachHang));
//                return newTaiKhoan;
//                });
//        KhachHang khachHang = khachHangDAO1.findByTaiKhoanKH(taiKhoan).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
//        response.setMessage("Tìm thấy khách hàng.");
//        if(khachHang.getHoTen() != null){
//        response.setResult(Map.of("exists", true, "tenKhachHang", khachHang.getHoTen()));
//        }
//        return response;
//    }

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






//    @GetMapping("/{id}")
//    public ResponseEntity<LichHenResponse> getLichHenById(@PathVariable String id) {
//        return ResponseEntity.ok(lichHenService.getLichHenById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<LichHenResponse> createLichHen(@RequestBody LichHenRequest requestDTO) {
//        return ResponseEntity.ok(lichHenService.createLichHen(requestDTO));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<LichHenResponse> updateLichHen(@PathVariable String id, @RequestBody LichHenRequest requestDTO) {
//        return ResponseEntity.ok(lichHenService.updateLichHen(id, requestDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteLichHen(@PathVariable String id) {
//        lichHenService.deleteLichHen(id);
//        return ResponseEntity.noContent().build();
//    }
}
