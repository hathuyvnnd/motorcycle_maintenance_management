package com.example.dto.request.phieudichvu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhieuDichVuRequest {
    private String idPhieuDichVu;
    private String idNhanVienTaoPhieu;
    private String idPhieuGNX;
    private Date ngayThucHien;
    private Boolean trangThaiSuaChua;
    private String tenNhanVienSuaChua;
}
