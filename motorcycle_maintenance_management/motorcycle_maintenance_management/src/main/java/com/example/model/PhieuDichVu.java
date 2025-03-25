package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="DichVu")
public class PhieuDichVu {
    @Id
    private String idPhieuDichVu;
    @ManyToOne
    @JoinColumn(name="NhanVien")
    private NhanVien idNhanVienTaoPhieu;
    private Date ngayThucHien;
    private Date ngayHoanThanh;
    private String trangThaiSuaChua;
    private String tenNhanVienSuaChua;

    @OneToMany(mappedBy = "dichVu")
    List<PhieuDichVuCT> phieuDichVuCTList;

    @OneToMany(mappedBy = "phieuDichVu")
    List<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;
}
