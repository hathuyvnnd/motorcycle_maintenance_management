package com.example.maintenece_motorcycle_management.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuSuDungPhuTungCT {
    private String idPhieuSuDungPhuTungCT;

    @ManyToOne
    @JoinColumn(name = "IdPhuTung")
    private PhuTung idPhuTung;

    private Integer soLuong;

    @ManyToOne
    @JoinColumn(name="IdPhieuDichVu")
    private PhieuDichVu idPhieuDichVu;
}
