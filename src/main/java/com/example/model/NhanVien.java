package com.example.model;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVien {
    private String idNhanVien;
    private String idTaiKhoan;
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
