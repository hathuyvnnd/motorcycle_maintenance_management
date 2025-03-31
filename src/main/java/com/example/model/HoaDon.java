package com.example.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDon")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idHoaDon")
public class HoaDon {
    @Id
    @Column(name = "IdHoaDon")
    private String idHoaDon;
    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "IdNhanVienTN")

    // @JsonBackReference
    private NhanVien nhanVien;

    @OneToOne
    @JoinColumn(name = "IdPhieuDichVu", unique = true)
    // @JsonManagedReference
    private PhieuDichVu phieuDichVuHD;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "PhuongThucThanhToan")
    private String phuongThucThanhToan;

    @Column(name = "TrangThaiThanhToan") // Ensure this matches the actual column name in the database
    private String trangThaiThanhToan;

    @Column(name = "TongTien")
    private float tongTien;

}
