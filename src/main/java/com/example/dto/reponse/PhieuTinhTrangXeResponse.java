package com.example.dto.reponse;

import com.example.model.HoaDon;
import com.example.model.LichHen;
import com.example.model.NhanVien;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuTinhTrangXeResponse {
    String idPhieuGNX;
    Date ngayNhan;
    String moTaTinhTrangXe;
    String bienSoXe;
    String nhanVien;
}
