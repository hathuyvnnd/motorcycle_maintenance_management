package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PhieuSuDungPhuTungCT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPhieuSuDungPhuTungCT")
public class PhieuSuDungPhuTungCT {
    @Id
    @Column(name = "IdPhieuSuDungPhuTungCT")
    private String idPhieuSuDungPhuTungCT;

    @ManyToOne
    @JoinColumn(name = "IdPhuTung")
    @JsonBackReference
    private PhuTung phuTung;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @ManyToOne
    @JoinColumn(name = "IdPhieuDichVu")
    @JsonBackReference
    private PhieuDichVu phieuDichVu;
}
