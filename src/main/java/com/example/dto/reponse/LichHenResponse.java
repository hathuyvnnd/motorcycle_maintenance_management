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
    Boolean trangThai;
    String bienSoXe;
    String ghiChu;
    String dichVu;
}
