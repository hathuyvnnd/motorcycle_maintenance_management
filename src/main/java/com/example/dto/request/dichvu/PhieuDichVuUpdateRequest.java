package com.example.dto.request.dichvu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.example.dto.request.phutung.PhuTungRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhieuDichVuUpdateRequest {
    private String idPhieuDichVu;               // Để biết phiếu nào cần update
    private String tenNhanVienSuaChua;    
    private List<String> listIdDichVu;          // Danh sách id dịch vụ để update
    private List<PhuTungRequest> danhSachPhuTung; // Danh sách phụ tùng cần cập nhật
    private String idLichHen;                  

}
