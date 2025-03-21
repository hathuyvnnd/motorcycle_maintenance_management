package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PhieuGhiNhanTinhTrangXe")
public class PhieuGhiNhanTinhTrangXe {
    @Id
    private String idPhieuGNX;
    private Date ngayNhan;
    private String moTaTinhTrangXe;
    private String bienSoXe;

    @ManyToOne
    @JoinColumn(name="IdNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang khachHang;


}
