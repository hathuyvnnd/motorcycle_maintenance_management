package com.example.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "NhanVien")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "idNhanVien")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNhanVien")
public class NhanVien {
    @Id
    @Column(name = "IdNhanVien", unique = true)
    private String idNhanVien;

    // @OneToOne(fetch = FetchType.LAZY)
    @OneToOne
    @JoinColumn(name = "SoDienThoai", referencedColumnName = "IdTaiKhoan", unique = true)
    @JsonIdentityReference(alwaysAsId = true)
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

    @OneToMany(mappedBy = "idNhanVienTaoPhieu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<PhieuDichVu> phieuDichVuList;

}
