package com.example.maintenece_motorcycle_management.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDon {
    private String idHoaDon;

    @ManyToOne
    @JoinColumn(name = "IdNhanVienTN")
    private NhanVien idNhanVien;

    @OneToOne
    @JoinColumn(name="IdPhieuDichVu")
    private PhieuDichVu idPhieuDichVu;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne
<<<<<<< Updated upstream:src/main/java/com/example/model/HoaDon.java
    @JoinColumn(name="IdKhachHang")
    private KhachHangVangLai idKhachHangVangLai;
=======
    @JoinColumn(name="IdKhachHangVangLai")
    private KhachHangVangLai khachHangVangLai;
>>>>>>> Stashed changes:src/main/java/com/example/maintenece_motorcycle_management/model/HoaDon.java

}
