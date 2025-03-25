package com.example.maintenece_motorcycle_management.model;

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
@Table(name = "DichVu")
public class DichVu {
    @Id
    private String idDichVu;
    private String tenDichVu;
    private float giaDichVu;
    private String trangThai;
    private String moTa;

    @OneToMany(mappedBy = "idPhieuDichVuCT")
    List<PhieuDichVuCT> phieuDichVuList;

    @OneToOne(mappedBy = "idPhieuDichVu")
    private HoaDon hoaDon;

    private Date ngayTao;


}
