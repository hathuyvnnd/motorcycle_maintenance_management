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
public class PhieuDichVuCreateRequest {
    private String idPhieuDichVu;
    private String idNhanVienTaoPhieu;
    private Date ngayThucHien;
    private Date ngayHoanThanh;
    private Boolean trangThaiSuaChua;
    private String tenNhanVienSuaChua;
    private String idPhieuGNX;
    private List<String> listIdDichVu;
    private List<PhuTungRequest> danhSachPhuTung;
    private String idLichHen;
    // private int soLuongCuaPhuTung;
}
