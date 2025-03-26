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
import com.example.service_impl.LichHenServiceImpl;
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
    TaiKhoanDao taiKhoanDAO;
    KhachHangDao khachHangDAO;

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




    @GetMapping("/check-phone")
    public ApiReponse<?> checkPhone(@RequestParam String phone) {
        ApiReponse<Object> response = new ApiReponse<>();

        // Kiểm tra nếu phone là null hoặc rỗng
        if (phone == null || phone.trim().isEmpty()) {
            response.setMessage("Số điện thoại không hợp lệ.");
            response.setResult(null);
            return response;
        }

        // Tìm tài khoản theo số điện thoại
        TaiKhoan taiKhoan = taiKhoanDAO.findById(phone).orElse(null);

        if (taiKhoan == null) {
            // Nếu tài khoản không tồn tại, tạo mới
            taiKhoan = new TaiKhoan();
            taiKhoan.setIdTaiKhoan(phone);
            taiKhoan.setMatKhau("123");  // Mật khẩu mặc định
            taiKhoan.setVaiTro("Khách hàng");
            taiKhoan.setTrangThai(true);
            taiKhoanDAO.save(taiKhoan);

            // Tạo khách hàng mới
            KhachHang newKhachHang = new KhachHang();
            newKhachHang.setIdKhachHang("KH" + phone);
            newKhachHang.setTaiKhoanKH(taiKhoan);
            newKhachHang.setSoDienThoai(phone);
            khachHangDAO.save(newKhachHang);

            response.setMessage("Tạo mới tài khoản và khách hàng thành công.");
            response.setResult(Map.of("exists", false, "newAccount", newKhachHang));
            return response;
        }

        // Nếu tài khoản đã tồn tại, tìm khách hàng liên kết
        KhachHang khachHang = khachHangDAO.findByTaiKhoanKH(taiKhoan);

        // Check nếu khách hàng NULL
        if (khachHang == null) {
            response.setMessage("Không tìm thấy khách hàng liên kết với tài khoản này.");
            response.setResult(null);
            return response;
        }

        // Nếu tìm thấy khách hàng, trả về tên
        response.setMessage("Tìm thấy khách hàng.");
        response.setResult(Map.of("exists", true, "tenKhachHang", khachHang.getHoTen()));

        return response;
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
