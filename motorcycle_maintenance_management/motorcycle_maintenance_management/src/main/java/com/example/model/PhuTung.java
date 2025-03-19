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
@Table(name="PhuTung")
public class PhuTung {
    @Id
    private String idPhuTung;
    private String tenPhuTung;
    private Float giaPhuTung;
    private Integer soLuongTonKho;

    @ManyToOne
    @JoinColumn(name = "IdLoaiPT")
    private LoaiPhuTung loaiPT;

    String moTa;
    private String thuongHieu;
    private String tinhTrang;
    private String phuHopLoaiXe;
    private String hinhAnh;
    private Date ngayNhapKho;
    private Date hanSuDung;

    @OneToMany(mappedBy = "phuTung")
    private List<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;


}
