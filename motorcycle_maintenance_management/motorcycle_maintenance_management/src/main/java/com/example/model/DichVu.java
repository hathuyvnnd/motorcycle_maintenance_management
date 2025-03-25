package com.example.model;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DichVu {
    private String idDichVu;
    private String tenDichVu;
    private float giaDichVu;
    private String trangThai;
    private String moTa;

    @OneToMany(mappedBy = "phieuDichVuCT")
    List<PhieuDichVuCT> phieuDichVuList;

    @OneToOne(mappedBy = "phieuDichVu")
    private HoaDon hoaDon;

    private Date ngayTao;


}
