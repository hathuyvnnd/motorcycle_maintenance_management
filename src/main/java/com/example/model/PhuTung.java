package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
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
    @JsonBackReference
    private LoaiPhuTung loaiPT;

    private String moTa;
    private String thuongHieu;
    private String tinhTrang;
    private String phuHopLoaiXe;
    private String hinhAnh;
    private Date ngayNhapKho;
    private Date hanSuDung;
    @JsonIgnore 
    @OneToMany(mappedBy = "phuTung")
    private List<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;


}
