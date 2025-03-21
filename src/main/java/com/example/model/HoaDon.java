package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="HoaDon")
public class HoaDon {
    @Id
    private String idHoaDon;

    @ManyToOne
    @JoinColumn(name = "IdNhanVienTN")
    private NhanVien nhanVien;

    @OneToOne
    @JoinColumn(name="IdPhieuDichVu")
    private PhieuDichVu phieuDichVu;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang khachHang;


}
