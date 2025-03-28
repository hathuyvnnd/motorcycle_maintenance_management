package com.example.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NhanVien")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "idNhanVien")
public class NhanVien {
    @Id
    @Column(name = "IdNhanVien", unique = true)
    private String idNhanVien;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SoDienThoai", referencedColumnName = "IdTaiKhoan", unique = true)
    private TaiKhoan taiKhoanNV;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "Email")
    private String email;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // cascade = CascadeType.REMOVE: dùng để xoá luôn bảng con khi xoá bảng cha
    // @JsonManagedReference
    Set<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // @JsonManagedReference
    Set<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "nhanVienTaoPhieu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // @JsonManagedReference
    Set<PhieuDichVu> phieuDichVuList;

}
