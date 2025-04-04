package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "PhieuDichVuCT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPhieuDichVuCT")
public class PhieuDichVuCT {
    @Id
    @Column(name = "IdPhieuDichVuCT")
    private String idPhieuDichVuCT;

    @Column(name = "GiaDichVu")
    private Float giaDichVu;

    @Column(name = "NgayThucHien")
    private Date ngayThucHien;

    @ManyToOne
    @JoinColumn(name = "IdDichVu")
    // @JsonBackReference
    private DichVu dichVu;

    @ManyToOne
    @JoinColumn(name = "IdPhieuDichVu")
    // @JsonBackReference
    private PhieuDichVu phieuDichVu;
}
