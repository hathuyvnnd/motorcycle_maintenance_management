package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PhieuDichVu")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPhieuDichVu")
public class PhieuDichVu {
    @Id
    @Column(name = "IdPhieuDichVu")
    private String idPhieuDichVu;

    @ManyToOne
    @JoinColumn(name = "IdNhanVienTaoPhieu")
    // @JsonBackReference
    private NhanVien nhanVienTaoPhieu;

    @Column(name = "NgayThucHien")

    private Date ngayThucHien;

    @Column(name = "NgayHoanThanh")
    private Date ngayHoanThanh;

    @Column(name = "TrangThaiSuaChua")
    private Boolean trangThaiSuaChua;

    @Column(name = "TenNhanVienSuaChua")
    private String tenNhanVienSuaChua;

    @OneToMany(mappedBy = "phieuDichVu", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<PhieuDichVuCT> phieuDichVuCTList;

    @OneToMany(mappedBy = "phieuDichVu", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;

    @OneToOne(mappedBy = "phieuDichVuHD")
    private HoaDon hoaDon;

}
