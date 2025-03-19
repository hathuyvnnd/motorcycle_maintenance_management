package com.example.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVien {
    private String idNhanVien;
    @OneToOne
    @JoinColumn(name = "IdTaiKhoan", referencedColumnName = "IdTaiKhoan")
    private TaiKhoan taiKhoan;
    private String soDienThoai;
    private String ten;
    private String diaChi;
    private String email;
    private String hinhAnh;

    @OneToMany(mappedBy = "idNhanVien")
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "idNhanVien")
    List<HoaDon> hoaDonList;
}
