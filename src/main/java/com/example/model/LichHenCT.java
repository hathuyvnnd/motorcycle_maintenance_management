package com.example.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LichHenCT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idLichHenCT")
public class LichHenCT {
    @Id
    @Column(name = "IdLichHenCT")
    private String idLichHenCT;

    @ManyToOne
    @JoinColumn(name = "IdDichVu")
    private DichVu idDichVu;

    @ManyToOne
    @JoinColumn(name = "IdLichHen")
    private LichHen idLichHen;

    @Column(name = "GhiChu")
    private String ghiChu;
}
