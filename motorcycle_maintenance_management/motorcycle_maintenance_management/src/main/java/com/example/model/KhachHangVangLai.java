package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KhachHangVangLai")
public class KhachHangVangLai {
    @Id
    private String idKhachHangVangLai;
    private String tenKhachHang;
    private String diaChi;
    private String soDienThoai;

    @OneToMany(mappedBy = "khachHangVangLai")
    List<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "khachHangVangLai")
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;
}
