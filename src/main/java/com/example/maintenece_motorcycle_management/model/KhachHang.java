package com.example.maintenece_motorcycle_management.model;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHang {
    private String idKhachHang;
    private String idTaiKhoan;
    private String soDienThoai;
    private String diaChi;
    private String email;
    private String hoTen;
    private String hinhAnh;
    private Date ngayDangKi;

    @OneToMany(mappedBy = "idKhachHang")
    List<LichHen> lichHenList;

    @OneToMany(mappedBy = "idKhachHang")
    List<PhieuGhiNhanTinhTrangXe> phieuGhiNhanTinhTrangXeList;

    @OneToMany(mappedBy = "idKhachHang")
    List<HoaDon> hoaDonList;


}
