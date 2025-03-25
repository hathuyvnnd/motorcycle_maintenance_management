package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="NhanVien")
public class NhanVien {
    @Id
    private String idNhanVien;
    private String idTaiKhoan;
    private String soDienThoai;
    private String ten;
    private String diaChi;
    private String email;
    private String hinhAnh;

    @OneToMany(mappedBy = "idNhanVienTaoPhieu")
    List<PhieuDichVu> phieuDichVuList;

    @OneToMany(mappedBy = "nhanVienPGN")
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "nhanVienTN")
    List<HoaDon> hoaDonList;

    @OneToOne
    @JoinColumn(name="IdTaiKhoan")
    TaiKhoan taiKhhoanNV;
}
