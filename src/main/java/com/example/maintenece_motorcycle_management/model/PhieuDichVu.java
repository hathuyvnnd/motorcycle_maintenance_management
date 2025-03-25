package com.example.maintenece_motorcycle_management.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuDichVu {
    private String idPhieuDichVu;
    @ManyToOne
    @JoinColumn(name="NhanVien")
    private NhanVien idNhanVienTaoPhieu;
    private Date ngayThucHien;
    private Date ngayHoanThanh;
    private String trangThaiSuaChua;
    private String tenNhanVienSuaChua;

    @OneToMany(mappedBy = "idDichVu")
    List<PhieuDichVuCT> phieuDichVuCTList;

    @OneToMany(mappedBy = "idPhieuDichVu")
    List<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;
}
