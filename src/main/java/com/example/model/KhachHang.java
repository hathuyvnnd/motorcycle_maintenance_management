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
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KhachHang")
public class KhachHang {
    @Id
    private String idKhachHang;
    private String idTaiKhoan;
    private String soDienThoai;
    private String diaChi;
    private String email;
    private String hoTen;
    private String hinhAnh;
    private Date ngayDangKi;

    @OneToMany(mappedBy = "khachHangLH")
    List<LichHen> lichHenList;

    @OneToMany(mappedBy = "khachHangPGN")
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "khachHang")
     List<HoaDon> hoaDonList;

    @OneToOne
    @JoinColumn(name = "IdTaiKhoan")
    TaiKhoan taiKhoanKH;
}
