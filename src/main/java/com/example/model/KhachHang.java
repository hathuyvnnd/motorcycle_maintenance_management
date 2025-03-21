package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KhachHang")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idKhachHang")
public class KhachHang {
    @Id
    @Column(name = "IdKhachHang")
    private String idKhachHang;

    @OneToOne
    @JoinColumn(name = "IdTaiKhoan", referencedColumnName = "IdTaiKhoan", unique = true)
    private TaiKhoan taiKhoanKH;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "Email")
    private String email;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @Column(name = "NgayDangKi")
    private Date ngayDangKi;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<LichHen> lichHenList;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<HoaDon> hoaDonList;

}
