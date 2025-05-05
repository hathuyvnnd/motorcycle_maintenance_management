package com.example.controller.employeectrl.staffrestcontroller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.reponse.ApiReponse;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.model.LichHen;
import com.example.model.NhanVien;
import com.example.service_impl.NhanVienServiceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/nhan-vien-thong-tin")
@CrossOrigin("*")
@RestController
public class NhanVienRestController {
    NhanVienServiceImpl sv;
    @PutMapping
        ApiReponse<NhanVien> updateUser(@RequestBody NhanVien request){
            ApiReponse<NhanVien> apiReponse = new ApiReponse<>();
            apiReponse.setResult(sv.updateLA(request));
            return apiReponse;
        }

    
}
