package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PhieuSuDungPhuTungCT")
public class PhieuSuDungPhuTungCT {
    @Id
    private String idPhieuSuDungPhuTungCT;

    @ManyToOne
    @JoinColumn(name = "IdPhuTung")
    private PhuTung phuTung;

    private Integer soLuong;

    @ManyToOne
    @JoinColumn(name="IdPhieuDichVu")
    private PhieuDichVu phieuDichVu;
}
