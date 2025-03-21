package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PhuTung")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPhuTung")
public class PhuTung {
    @Id
    @Column(name = "IdPhuTung")
    private String idPhuTung;

    @Column(name = "TenPhuTung")
    private String tenPhuTung;

    @Column(name = "GiaPhuTung")
    private Float giaPhuTung;

    @Column(name = "SoLuongTonKho")
    private Integer soLuongTonKho;

    @ManyToOne
    @JoinColumn(name = "IdLoaiPT")
    @JsonBackReference
    private LoaiPhuTung loaiPT;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "ThuongHieu")
    private String thuongHieu;

    @Column(name = "TinhTrang")
    private String tinhTrang;

    @Column(name = "PhuHopLoaiXe")
    private String phuHopLoaiXe;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @Column(name = "NgayNhapKho")
    private Date ngayNhapKho;

    @Column(name = "HanSuDung")
    private Date hanSuDung;

    @OneToMany(mappedBy = "phuTung")
    @JsonManagedReference
    private List<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;

}
