package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NhanVien")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNhanVien")
public class NhanVien {
    @Id
    @Column(name = "IdNhanVien", unique = true)
    private String idNhanVien;

    @OneToOne
    @JoinColumn(name = "IdTaiKhoan", referencedColumnName = "IdTaiKhoan", unique = true)
    private TaiKhoan taiKhoanNV;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "Email")
    private String email;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @OneToMany(mappedBy = "nhanVien")
    @JsonManagedReference
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "nhanVien")
    @JsonManagedReference
    List<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "nhanVienTaoPhieu")
    @JsonManagedReference
    List<PhieuDichVu> phieuDichVuList;
}
