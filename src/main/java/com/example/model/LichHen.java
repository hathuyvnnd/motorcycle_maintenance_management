package com.example.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichHen {
    private String idLichHen;
    private Date thoiGian;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne
    @JoinColumn(name="IdLoaiXe")
    private LoaiXe idLoaiXe;

    private String trangThai;
    private String bienSoXe;
    private String ghiChu;
    private String dichVu;
}
