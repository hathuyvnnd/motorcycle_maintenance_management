package com.example.dto.request.dichvu;

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
public class PhieuDichVuCreateRequest {
    private String idPhieuDichVu;
    private String idNhanVienTaoPhieu;
    private Date ngayThucHien;
    private Date ngayHoanThanh;
    private String trangThaiSuaChua;
    private String tenNhanVienSuaChua;
    private Boolean trangThai;
    private String idPhieuGNX;
    private List<String> listIdDichVu;
    private String idLichHen;
}
