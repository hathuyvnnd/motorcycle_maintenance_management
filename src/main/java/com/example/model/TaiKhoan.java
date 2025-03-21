package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VaiTro", discriminatorType = DiscriminatorType.STRING)
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @Id
    @Column(name = "IdTaiKhoan")
    private String idTaiKhoan;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    // @Column(name = "VaiTro")
    // private String vaiTro;

    // @OneToOne(mappedBy = "taiKhoanNV")
    // private NhanVien nhanVien;

    // @OneToOne(mappedBy = "taiKhoanKH")
    // private KhachHang khachHang;
}
