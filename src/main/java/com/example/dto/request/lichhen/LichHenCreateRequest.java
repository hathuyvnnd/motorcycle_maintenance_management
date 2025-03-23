package com.example.dto.request.lichhen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichHenCreateRequest {
    private String idLichHen;
    private Date thoiGian;
    private String idKhachHang;
    private String idLoaiXe;
    private String trangThai;
    private String bienSoXe;
    private String ghiChu;
    private String dichVu;
}
