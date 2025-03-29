package com.example.dto.request.lichhen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LichHenCreateRequest {
    private String idLichHen;
    private String tenKhachHang;
    private Date thoiGian;
    private String idKhachHang;
    private String idLoaiXe;
    private String trangThai;
    private String bienSoXe;
    private String ghiChu;
    private String dichVu;
}
