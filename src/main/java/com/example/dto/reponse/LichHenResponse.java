package com.example.dto.reponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichHenResponse {
    String idLichHen;
    Date thoiGian;
    String idKhachHang;
    String idLoaiXe;
    String trangThai;
    String bienSoXe;
    String ghiChu;
    String dichVu;
    String tenKhachHang;
    String soDienThoai;
}
