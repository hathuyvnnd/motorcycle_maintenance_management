package com.example.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhuTung {
    private String idPhuTung;
    private String tenPhuTung;
    private Float giaPhuTung;
    private Integer soLuongTonKho;

    @ManyToOne
    @JoinColumn(name = "IdLoaiPT")
    private LoaiPhuTung idLoaiPT;

    String moTa;
    private String thuongHieu;
    private String tinhTrang;
    private String phuHopLoaiXe;
    private String hinhAnh;
    private Date ngayNhapKho;
    private Date hanSuDung;

    @OneToMany(mappedBy = "idPhuTung")
    List<PhieuSuDungPhuTungCT> phieuSuDungPhuTungCTList;


}
