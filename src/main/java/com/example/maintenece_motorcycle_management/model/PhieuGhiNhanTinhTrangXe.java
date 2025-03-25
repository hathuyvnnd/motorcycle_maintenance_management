package com.example.maintenece_motorcycle_management.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuGhiNhanTinhTrangXe {
    private String idPhieuGNX;
    private Date ngayNhan;
    private String moTaTinhTrangXe;
    private String bienSoXe;

    @ManyToOne
    @JoinColumn(name="IdNhanVien")
    private NhanVien idNhanVien;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne
<<<<<<< Updated upstream:src/main/java/com/example/model/PhieuGhiNhanTinhTrangXe.java
    @JoinColumn(name="IdKhachHang")
    private KhachHangVangLai idKhachHangVangLai;
=======
    @JoinColumn(name="IdKhachHangVangLai")
    private KhachHangVangLai khachHangVangLai;
>>>>>>> Stashed changes:src/main/java/com/example/maintenece_motorcycle_management/model/PhieuGhiNhanTinhTrangXe.java
}
