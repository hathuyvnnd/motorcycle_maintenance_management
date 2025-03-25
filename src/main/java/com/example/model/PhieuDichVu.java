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
@Table(name="PhieuDichVu")
public class PhieuDichVu {
    @Id
    private String idPhieuDichVu;

    @ManyToOne
    @JoinColumn(name="IdNhanVienTaoPhieu")
    private NhanVien idNhanVienTaoPhieu;
    
    private Date ngayThucHien;
    private Date ngayHoanThanh;
    private String trangThaiSuaChua;
    private String tenNhanVienSuaChua;

    @OneToMany(mappedBy = "phieuDichVuCT")
    List<PhieuDichVuCT> phieuDichVUCTList;

    @OneToOne(mappedBy = "phieuDichVu")
    private HoaDon hoaDon;
}
