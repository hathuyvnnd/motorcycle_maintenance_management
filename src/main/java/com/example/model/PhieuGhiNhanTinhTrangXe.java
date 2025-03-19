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
public class PhieuGhiNhanTinhTrangXe {
    private String idPhieuGNX;
    private Date ngayNhan;
    private String moTaTinhTrangXe;
    private String bienSoXe;

    @ManyToOne
    @JoinColumn(name="IdNhanVien")
    private NhanVien idNhanVien;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHangVangLai idKhachHangVangLai;
}
