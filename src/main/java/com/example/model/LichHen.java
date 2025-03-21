package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LichHen")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idLichHen")
public class LichHen {
    @Id
    @Column(name = "IdLichHen")
    private String idLichHen;
    private Date thoiGian;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    @JsonBackReference
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "IdLoaiXe")
    @JsonBackReference
    private LoaiXe idLoaiXe;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "BienSoXe")
    private String bienSoXe;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "DichVu")
    private String dichVu;
}
