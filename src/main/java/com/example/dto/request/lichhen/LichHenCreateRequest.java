package com.example.dto.request.lichhen;

import com.example.model.LichHenCT;
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
public class LichHenCreateRequest {
    private String idLichHen;
    private String tenKhachHang;
    private Date thoiGian;
    private String idKhachHang;
    private String idLoaiXe;
    private String trangThai;
    private String bienSoXe;
    private String ghiChu;
    private List<String> listIdDichVu;
}
