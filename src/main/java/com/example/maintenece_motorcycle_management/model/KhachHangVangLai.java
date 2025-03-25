package com.example.maintenece_motorcycle_management.model;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangVangLai {
    private String idKhachHangVangLai;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;

    @OneToMany(mappedBy = "idKhachHangVangLai")
    List<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "idKhachHangVangLai")
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;
}
