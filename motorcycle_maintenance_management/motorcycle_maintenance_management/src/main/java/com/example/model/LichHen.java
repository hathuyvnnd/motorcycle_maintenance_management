package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="LichHen")
public class LichHen {
    @Id
    private String idLichHen;
    private Date thoiGian;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name="IdLoaiXe")
    private LoaiXe idLoaiXe;

    private String trangThai;
    private String bienSoXe;
    private String ghiChu;
    private String dichVu;
}
