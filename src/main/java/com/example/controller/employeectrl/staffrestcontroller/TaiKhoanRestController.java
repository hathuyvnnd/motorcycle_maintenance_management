package com.example.controller.employeectrl.staffrestcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dto.reponse.ApiReponse;
import com.example.dto.request.taikhoan.DoiMatKhauRequest;
import com.example.model.LichHen;
import com.example.model.NhanVien;
import com.example.model.TaiKhoan;
import com.example.service_impl.NhanVienServiceImpl;
import com.example.service_impl.TaiKhoanServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/doi-mat-khau")
@CrossOrigin("*")
@RestController
public class TaiKhoanRestController {
    TaiKhoanServiceImpl tksv;
    NhanVienServiceImpl nvsv;
     PasswordEncoder passwordEncoder;
    @GetMapping
    ApiReponse<TaiKhoan> findTaiKhoanByIdNhanVien(@RequestParam String id){
        ApiReponse<TaiKhoan> reponse = new ApiReponse<>();
        reponse.setResult(tksv.findById(id));
        return reponse;
    }
    @PutMapping
    ApiReponse<TaiKhoan> doiMatKhauStaff(@RequestParam String id, @RequestParam String mk){
        // tksv.doiMatKhauStaff(id, mk);
        ApiReponse<TaiKhoan> reponse = new ApiReponse<>();
        reponse.setResult(tksv.doiMatKhauStaff(id, mk));
        return reponse;
    }
    @PutMapping("/doi")
public ApiReponse<?> doiMatKhauStaff(@RequestBody DoiMatKhauRequest request) {
    ApiReponse<TaiKhoan> response = new ApiReponse<>();
    TaiKhoan tk = tksv.findById(request.getId());

    if (tk == null) {
        response.setMessage("Không tìm thấy tài khoản.");
        return response;
    }

    if (!passwordEncoder.matches(request.getMatKhauCu(), tk.getMatKhau())) {
        response.setMessage("Mật khẩu cũ không chính xác.");
        return response;
    }

    String encodedNewPassword = passwordEncoder.encode(request.getMatKhauMoi());
    tk.setMatKhau(encodedNewPassword);
    tksv.update1(tk);
    response.setResult(tk);
    response.setMessage("Đổi mật khẩu thành công.");
    return response;
}

        private final String UPLOAD_DIR = "src/main/resources/static/images/nhan-vien/";

        @PostMapping("/upload-image")
        public ResponseEntity<ApiReponse<NhanVien>> uploadImageAndUpdateEmployee(
                @RequestPart("file") MultipartFile file, 
                @RequestPart("nhanVien") NhanVien nhanVien) {
            try {
                // Lưu ảnh lên thư mục
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    
                // Lấy URL của ảnh đã upload
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/nhan-vien/")
                        .path(fileName)
                        .toUriString();
    
                // Cập nhật thông tin nhân viên với URL ảnh
                nhanVien.setHinhAnh(imageUrl);
    
                // Cập nhật thông tin nhân viên trong database
                NhanVien updatedNhanVien = nvsv.updateLA(nhanVien);
    
                ApiReponse<NhanVien> response = new ApiReponse<>();
                response.setResult(updatedNhanVien);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiReponse<>());
            }
        }
    
        // Endpoint để lấy ảnh của nhân viên
        @GetMapping("/images/nhan-vien/{fileName}")
        public ResponseEntity<byte[]> getImage(@RequestParam String fileName) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                byte[] image = Files.readAllBytes(filePath);
    
                return ResponseEntity.ok()
                        .header("Content-Type", "image/jpeg")  // hoặc loại ảnh khác tùy vào loại ảnh của bạn
                        .body(image);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

        @PutMapping("/updateWithFile/{id}")
        public NhanVien updateWithFile(
                @PathVariable String id,
                @RequestPart("nhanVien") NhanVien nv,
                @RequestPart("file") MultipartFile file) {
    
            if (!file.isEmpty()) {
                try {
                    File uploadFile = new File(UPLOAD_DIR + file.getOriginalFilename());
                    file.transferTo(uploadFile);
                    nv.setHinhAnh(file.getOriginalFilename());
                } catch (IOException e) {
                    throw new RuntimeException("Lỗi khi upload file", e);
                }
            } else {
                // Nếu không có file mới, giữ nguyên ảnh cũ
                NhanVien existing = nvsv.findById(id);
            nv.setHinhAnh(existing.getHinhAnh());
            }
    
            nv.setIdNhanVien(id);
            nvsv.update(nv);
            return nv;
        }
    
}
